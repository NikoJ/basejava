package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    protected abstract Integer getPosition(String uuid);

    protected abstract void executeDelete(int position);

    protected abstract void executeSave(Resume resume, int position);

    @Override
    protected boolean isExist(Object position) {
        return (Integer) position >= 0;
    }

    @Override
    protected void performUpdate(Resume resume, Object position) {
        storage[(Integer) position] = resume;
    }

    @Override
    protected void performSave(Resume resume, Object position) {
        if (size < STORAGE_LIMIT) {
            executeSave(resume, (Integer) position);
            size++;
        } else {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
    }

    @Override
    protected void performDelete(Object position) {
        executeDelete((Integer) position);
        storage[size--] = null;
    }

    @Override
    protected Resume performGet(Object position) {
        return storage[(Integer) position];
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }
}
