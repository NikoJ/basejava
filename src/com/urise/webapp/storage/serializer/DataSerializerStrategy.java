package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DataSerializerStrategy implements SerializerStrategy {
    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();
            writeObject(dos, contacts.entrySet(), entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });

            Map<SectionType, Section> sections = resume.getSections();
            writeObject(dos, sections.entrySet(), entry -> {
                SectionType sectionType = entry.getKey();
                Section section = entry.getValue();
                dos.writeUTF(sectionType.name());
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dos.writeUTF(((TextBoxSection) section).getText());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        writeObject(dos, ((ListSection) section).getList(), dos::writeUTF);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        writeObject(dos, ((CompanySection) section).getCompanies(), company -> {
                            dos.writeUTF(company.getSitePage().getName());
                            dos.writeUTF(company.getSitePage().getUrl());
                            writeObject(dos, company.getRoles(), role -> {
                                writeLocalDate(dos, role.getDateStart());
                                writeLocalDate(dos, role.getDateEnd());
                                dos.writeUTF(role.getName());
                                dos.writeUTF(role.getDescription());
                            });
                        });
                        break;
                }
            });
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            readObject(dis, () -> {
                ContactType contactType = ContactType.valueOf(dis.readUTF());
                String val = dis.readUTF();
                resume.addContacts(contactType, val);
            });
            readObject(dis, () -> {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                resume.addSections(sectionType, getSectionForRead(dis, sectionType));
            });
            return resume;
        }
    }

    private void writeLocalDate(DataOutputStream dos, LocalDate date) throws IOException {
        dos.writeInt(date.getYear());
        dos.writeInt(date.getMonth().getValue());
    }

    private LocalDate readLocalDate(DataInputStream dis) throws IOException {
        return LocalDate.of(dis.readInt(), dis.readInt(), 1);
    }

    private Section getSectionForRead(DataInputStream dis, SectionType sectionType) throws IOException {
        switch (sectionType) {
            case PERSONAL:
            case OBJECTIVE:
                return new TextBoxSection(dis.readUTF());
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                return new ListSection(readList(dis, dis::readUTF));
            case EXPERIENCE:
            case EDUCATION:
                return new CompanySection(
                        readList(dis, () -> new Company(
                                new Site(dis.readUTF(), dis.readUTF()),
                                readList(dis, () -> new Role(
                                        readLocalDate(dis), readLocalDate(dis), dis.readUTF(), dis.readUTF()
                                ))
                        ))
                );
            default:
                throw new IllegalStateException();
        }
    }

    private <T> void writeObject(DataOutputStream dos, Collection<T> collection, FunctionWriter<T> writer) throws IOException {
        dos.writeInt(collection.size());
        for (T t : collection) {
            writer.write(t);
        }
    }

    private void readObject(DataInputStream dis, FunctionReader reader) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            reader.read();
        }
    }

    private <T> List<T> readList(DataInputStream dis, FunctionReaderList<T> reader) throws IOException {
        int size = dis.readInt();
        List<T> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(reader.readList());
        }
        return list;
    }

    @FunctionalInterface
    private interface FunctionWriter<T> {
        void write(T t) throws IOException;
    }

    @FunctionalInterface
    private interface FunctionReader {
        void read() throws IOException;
    }

    @FunctionalInterface
    private interface FunctionReaderList<T> {
        T readList() throws IOException;
    }
}