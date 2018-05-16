package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    protected abstract Integer getPosition(String uuid);

    protected abstract void executeDelete(int position);

    protected abstract void executeSave(Resume resume, int position);

    @Override
    protected boolean isExist(Integer position) {
        return position >= 0;
    }

    @Override
    protected void performUpdate(Resume resume, Integer position) {
        storage[position] = resume;
    }

    @Override
    protected void performSave(Resume resume, Integer position) {
        if (size < STORAGE_LIMIT) {
            executeSave(resume, position);
            size++;
        } else {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
    }

    @Override
    protected void performDelete(Integer position) {
        executeDelete(position);
        storage[size--] = null;
    }

    @Override
    protected Resume performGet(Integer position) {
        return storage[position];
    }

    @Override
    public List<Resume> performSorted() {
        return Arrays.asList(Arrays.copyOf(storage, size));
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
}
