package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.model.Section;
import com.urise.webapp.model.SectionType;
import com.urise.webapp.sql.SqlHelper;
import com.urise.webapp.util.JsonParser;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.doQuery("DELETE FROM resume");
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.transactionalExecute(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "INSERT INTO resume(uuid, full_name) " +
                    "VALUES (?,?)")) {
                preparedStatement.setString(1, resume.getUuid());
                preparedStatement.setString(2, resume.getFullName());
                preparedStatement.execute();
            }
            insertContacts(connection, resume);
            insertSections(connection, resume);
            return null;
        });
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.transactionalExecute(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                preparedStatement.setString(1, resume.getFullName());
                preparedStatement.setString(2, resume.getUuid());
                if (preparedStatement.executeUpdate() == 0) {
                    throw new NotExistStorageException(resume.getUuid());
                }
                deleteContacts(connection, resume);
                deleteSections(connection, resume);
                insertContacts(connection, resume);
                insertSections(connection, resume);
                return null;
            }
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.transactionalExecute(connection -> {
            Resume resume;
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM resume WHERE uuid = ?")) {
                preparedStatement.setString(1, uuid);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) {
                    throw new NotExistStorageException(uuid);
                }
                resume = new Resume(uuid, resultSet.getString("full_name"));
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM contact WHERE resume_uuid = ?")) {
                preparedStatement.setString(1, uuid);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    addContact(resultSet, resume);
                }
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM section WHERE resume_uuid = ?")) {
                preparedStatement.setString(1, uuid);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    addSection(resultSet, resume);
                }
            }
            return resume;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.doQuery("DELETE FROM resume WHERE uuid = ?", (preparedStatement -> {
            preparedStatement.setString(1, uuid);
            if (preparedStatement.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        }));
    }

    @Override
    public int size() {
        return sqlHelper.doQuery("SELECT count(*) FROM resume", (preparedStatement -> {
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() ? resultSet.getInt(1) : 0;
        }));
    }

//    @Override
//    public List<Resume> getAllSorted() {
//        return sqlHelper.doQuery("" +
//                "SELECT * FROM resume res " +
//                " LEFT JOIN contact con ON res.uuid = con.resume_uuid " +
//                " ORDER BY full_name, uuid", preparedStatement -> {
//            ResultSet resultSet = preparedStatement.executeQuery();
//            Map<String, Resume> map = new LinkedHashMap<>();
//            while (resultSet.next()) {
//                String uuid = resultSet.getString("uuid");
//                Resume resume = map.get(uuid);
//                if (resume == null) {
//                    resume = new Resume(uuid, resultSet.getString("full_name"));
//                    map.put(uuid, resume);
//                }
//                addContact(resultSet, resume);
//            }
//            return new ArrayList<>(map.values());
//        });
//    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.transactionalExecute(connection -> {
            Map<String, Resume> resumeMap = new LinkedHashMap<>();
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM resume ORDER BY full_name, uuid")) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String uuid = resultSet.getString("uuid");
                    resumeMap.put(uuid, new Resume(uuid, resultSet.getString("full_name")));
                }
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM contact")) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Resume r = resumeMap.get(resultSet.getString("resume_uuid"));
                    addContact(resultSet, r);
                }
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM section")) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Resume r = resumeMap.get(resultSet.getString("resume_uuid"));
                    addSection(resultSet, r);
                }
            }
            return new ArrayList<>(resumeMap.values());
        });
    }

    private void addContact(ResultSet resultSet, Resume resume) throws SQLException {
        String value = resultSet.getString("value");
        if (value != null) {
            resume.addContacts(ContactType.valueOf(resultSet.getString("type")), value);
        }
    }

    private void addSection(ResultSet resultSet, Resume resume) throws SQLException {
        String content = resultSet.getString("content");
        if (content != null) {
            SectionType type = SectionType.valueOf(resultSet.getString("type"));
            resume.addSections(type, JsonParser.read(content, Section.class));
        }
    }

    private void insertContacts(Connection connection, Resume resume) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("" +
                "INSERT INTO contact(resume_uuid, type, value) " +
                "VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> e : resume.getContacts().entrySet()) {
                preparedStatement.setString(1, resume.getUuid());
                preparedStatement.setString(2, e.getKey().name());
                preparedStatement.setString(3, e.getValue());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }
    }

    private void insertSections(Connection connection, Resume resume) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("" +
                "INSERT INTO section(resume_uuid, type, content) " +
                "VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, Section> e : resume.getSections().entrySet()) {
                preparedStatement.setString(1, resume.getUuid());
                preparedStatement.setString(2, e.getKey().name());
                Section section = e.getValue();
                preparedStatement.setString(3, JsonParser.write(section, Section.class));
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }
    }

    private void deleteSections(Connection connection, Resume resume) throws SQLException {
        deleteAttributes(connection, resume, "DELETE FROM section WHERE resume_uuid=?");
    }


    private void deleteContacts(Connection connection, Resume resume) throws SQLException {
        deleteAttributes(connection, resume, "DELETE FROM contact WHERE resume_uuid=?");
    }

    private void deleteAttributes(Connection connection, Resume resume, String sql) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, resume.getUuid());
            preparedStatement.execute();
        }
    }
}
