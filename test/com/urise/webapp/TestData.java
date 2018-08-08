package com.urise.webapp;

import com.urise.webapp.model.*;

import java.time.Month;
import java.util.UUID;

public class TestData {
    public static final String UUID_1 = UUID.randomUUID().toString();
    public static final String UUID_2 = UUID.randomUUID().toString();
    public static final String UUID_3 = UUID.randomUUID().toString();
    public static final String UUID_4 = UUID.randomUUID().toString();

    public static final Resume RESUME_1;
    public static final Resume RESUME_2;
    public static final Resume RESUME_3;
    public static final Resume RESUME_4;

    static {
        RESUME_1 = new Resume(UUID_1, "A");
        RESUME_2 = new Resume(UUID_2, "C");
        RESUME_3 = new Resume(UUID_3, "B");
        RESUME_4 = new Resume(UUID_4, "D");

        RESUME_1.addContacts(ContactType.PHONE, "+999999999");
        RESUME_2.addContacts(ContactType.HEADHUNTER, "headhunter");
        RESUME_3.addContacts(ContactType.GITHUB, "+github");
        RESUME_4.addContacts(ContactType.EMAIL, "+email");
        RESUME_1.addSections(SectionType.ACHIEVEMENT, new ListSection("A1", "A2", "A3"));
        RESUME_1.addSections(SectionType.EDUCATION, new CompanySection(new Company("ЛЭТИ", "URL",
                new Role(2016, Month.JANUARY, 2018, Month.APRIL, "Бакалавр"))));

        RESUME_2.addSections(SectionType.PERSONAL, new TextBoxSection("PERSONAL"));
        RESUME_2.addSections(SectionType.OBJECTIVE, new TextBoxSection("OBJECTIVE"));
        RESUME_2.addSections(SectionType.QUALIFICATIONS, new ListSection("Q1", "Q2", "Q3"));

        RESUME_3.addSections(SectionType.PERSONAL, new TextBoxSection("PERSONAL"));
        RESUME_3.addSections(SectionType.EXPERIENCE, new CompanySection(new Company("Title", "URL",
                new Role(2016, Month.JANUARY, 2018, Month.APRIL, "Инженер", "Работал в лаборатории"),
                new Role(2015, Month.JANUARY, 2017, Month.APRIL, "Инженер", "Работал в лаборатории"))));

    }
}
