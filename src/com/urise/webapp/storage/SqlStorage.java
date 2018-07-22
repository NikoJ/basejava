package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
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

            try (PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "INSERT INTO contact(resume_uuid, type, value) " +
                    "VALUES (?,?,?)")) {
                for (Map.Entry<ContactType, String> e : resume.getContacts().entrySet()) {
                    preparedStatement.setString(1, resume.getUuid());
                    preparedStatement.setString(2, e.getKey().getTitle());
                    preparedStatement.setString(3, e.getValue());
                    preparedStatement.addBatch();
                    return null;
                }
                preparedStatement.executeBatch();
            }
            return null;
        });
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.doQuery("UPDATE resume SET full_name = ? WHERE uuid = ?", (preparedStatement -> {
            preparedStatement.setString(1, resume.getFullName());
            preparedStatement.setString(2, resume.getUuid());
            if (preparedStatement.executeUpdate() == 0) {
                throw new NotExistStorageException(resume.getUuid());
            }
            return null;
        }));
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.doQuery("" +
                "SELECT * FROM resume res " +
                " LEFT JOIN contact conts " +
                " ON res.uuid = conts.resume_uuid " +
                " WHERE res.uuid = ?", (preparedStatement -> {
            preparedStatement.setString(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new NotExistStorageException(uuid);
            }
            Resume resume = new Resume(uuid, resultSet.getString("full_name"));
            do {
                String value = resultSet.getString("value");
                ContactType type = ContactType.valueOf(resultSet.getString("type"));
                resume.addContacts(type, value);
            } while (resultSet.next());
            return resume;
        }));
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
    public List<Resume> getAllSorted() {
        return sqlHelper.doQuery("SELECT * FROM resume ORDER BY full_name,uuid", (preparedStatement -> {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Resume> resumes = new ArrayList<>();
            while (resultSet.next()) {
                resumes.add(new Resume(resultSet.getString("uuid"), resultSet.getString("full_name")));
            }
            return resumes;
        }));
    }

    @Override
    public int size() {
        return sqlHelper.doQuery("SELECT count(*) FROM resume", (preparedStatement -> {
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() ? resultSet.getInt(1) : 0;
        }));
    }
}
