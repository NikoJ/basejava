package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractStorageTest {
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";

    private static final Resume RESUME_1;
    private static final Resume RESUME_2;
    private static final Resume RESUME_3;
    private static final Resume RESUME_4;

    static {
        RESUME_1 = new Resume(UUID_1, "A");
        RESUME_2 = new Resume(UUID_2, "C");
        RESUME_3 = new Resume(UUID_3, "B");
        RESUME_4 = new Resume(UUID_4, "D");
        RESUME_1.addContacts(ContactType.PHONE, "+999999999");
        RESUME_2.addSections(SectionType.OBJECTIVE, new TextBoxSection("OBJECTIVE"));
        RESUME_3.addSections(SectionType.PERSONAL, new TextBoxSection("PERSONAL"));
        RESUME_1.addSections(SectionType.ACHIEVEMENT, new ListSection("A1", "A2", "A3"));
        RESUME_2.addSections(SectionType.QUALIFICATIONS, new ListSection("Q1", "Q2", "Q3"));
        RESUME_3.addSections(SectionType.EXPERIENCE, new CompanySection(new Company("Title", "URL",
                new Role("02.01.2016", "02.01.2018", "Инженер", "Работал в лаборатории"),
                new Role("02.01.2016", "02.01.2018", "Инженер", "Работал в лаборатории"))));
        RESUME_1.addSections(SectionType.EDUCATION, new CompanySection(new Company("ЛЭТИ", "URL",
                new Role("02.01.2013", "02.01.2017", "Бакалавр"))));
    }

    protected Storage storage;

    AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @After
    public void tearDown() throws Exception {
        storage.clear();
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void save() throws Exception {
        storage.save(RESUME_4);
        Assert.assertEquals(4, storage.size());
        Assert.assertEquals(RESUME_4, storage.get(UUID_4));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() throws Exception {
        storage.save(RESUME_3);
    }

    @Test
    public void update() throws Exception {
        Resume testResume = new Resume(UUID_1, "5");
        storage.update(testResume);
        Assert.assertTrue(testResume == storage.get(UUID_1));
    }

    @Test
    public void delete() throws Exception {
        storage.delete(UUID_1);
        Assert.assertEquals(2, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete("dummy");
    }

    @Test
    public void size() throws Exception {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void get() throws Exception {
        Assert.assertEquals(RESUME_1, storage.get(UUID_1));
        Assert.assertEquals(RESUME_2, storage.get(UUID_2));
        Assert.assertEquals(RESUME_3, storage.get(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }

    @Test
    public void getAllSorted() throws Exception {
        List<Resume> sorted = storage.getAllSorted();
        Assert.assertEquals(3, sorted.size());
        Assert.assertEquals(Arrays.asList(RESUME_1, RESUME_3, RESUME_2), sorted);
    }
}