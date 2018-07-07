package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataSerializerStrategy implements SerializerStrategy {
    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }

            Map<SectionType, Section> sections = resume.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, Section> entry : resume.getSections().entrySet()) {
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
                        List<String> listSectionList = (((ListSection) section).getList());
                        dos.writeInt(listSectionList.size());
                        for (String listSection : listSectionList) {
                            dos.writeUTF(listSection);
                        }
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        List<Company> companyList = (((CompanySection) section).getCompanies());
                        dos.writeInt(companyList.size());
                        for (Company company : companyList) {
                            dos.writeUTF(company.getSitePage().getName());
                            dos.writeUTF(company.getSitePage().getUrl());

                            List<Role> rolesList = company.getRoles();
                            dos.writeInt(rolesList.size());
                            for (Role role : rolesList) {
                                writeLocalDate(dos, role.getDateStart());
                                writeLocalDate(dos, role.getDateEnd());
                                dos.writeUTF(role.getDescription());
                                dos.writeUTF(role.getName());
                            }
                        }
                        break;
                }
            }
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                ContactType contactType = ContactType.valueOf(dis.readUTF());
                String val = dis.readUTF();
                resume.addContacts(contactType, val);
            }

            size = dis.readInt();
            for (int i = 0; i < size; i++) {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                resume.addSections(sectionType, getSectionForRead(dis, sectionType));
            }
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
                int size = dis.readInt();
                List<String> listSectionList = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                    listSectionList.add(dis.readUTF());
                }
                return new ListSection(listSectionList);
            case EXPERIENCE:
            case EDUCATION:
                size = dis.readInt();
                List<Company> companyList = new ArrayList<>(size);
                for (int i = 0; i < size; i++) {
                    int length = dis.readInt();
                    List<Role> roleList = new ArrayList<>(length);
                    for (int j = 0; j < length; j++) {
                        roleList.add(new Role(readLocalDate(dis), readLocalDate(dis), dis.readUTF(), dis.readUTF()));
                    }
                    companyList.add(new Company(new Site(dis.readUTF(), dis.readUTF()), roleList));
                }
                return new CompanySection(companyList);
            default:
                throw new IllegalStateException();
        }
    }
}
