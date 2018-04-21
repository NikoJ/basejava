package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getPosition(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void selectDeletePosition(int position) {
        size--;
        storage[position] = storage[size];
        storage[size] = null;
    }

    @Override
    protected void selectSavePosition(Resume resume, int position) {
        storage[size] = resume;
    }
}
