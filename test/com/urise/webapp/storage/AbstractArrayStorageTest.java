package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public abstract class AbstractArrayStorageTest {
    protected static final String UUID_1 = "uuid1";
    protected static final Resume RESUME_1 = new Resume(UUID_1);
    protected static final String UUID_2 = "uuid2";
    protected static final Resume RESUME_2 = new Resume(UUID_2);
    protected static final String UUID_3 = "uuid3";
    protected static final Resume RESUME_3 = new Resume(UUID_3);
    protected static final String UUID_4 = "uuid4";
    protected static final Resume RESUME_4 = new Resume(UUID_4);
    protected Storage storage;

    public AbstractArrayStorageTest(Storage storage) {
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

    @Test
    public void update() throws Exception {
        Resume testResume = new Resume(UUID_1);
        storage.update(testResume);
        Assert.assertTrue(testResume == storage.get(UUID_1));
    }

    @Test
    public void delete() throws Exception {
        storage.delete(UUID_1);
        Assert.assertEquals(2, storage.size());
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

    @Test
    public void getAll() throws Exception {
        Resume[] resumes = storage.getAll();
        Assert.assertEquals(3, resumes.length);
        Assert.assertEquals(RESUME_1, resumes[0]);
        Assert.assertEquals(RESUME_2, resumes[1]);
        Assert.assertEquals(RESUME_3, resumes[2]);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }

    @Test(expected = ExistStorageException.class)
    public void getExist() throws Exception {
        storage.save(RESUME_3);
    }

    @Test(expected = StorageException.class)
    public void getOverflow() throws Exception {
        for (int i = storage.size(); i <= AbstractArrayStorage.STORAGE_LIMIT; i++) {
            storage.save(new Resume());
        }
        storage.save(new Resume());
    }
}