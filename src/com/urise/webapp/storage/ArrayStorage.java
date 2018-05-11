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
    protected void executeDelete(int position) {
        storage[position] = storage[size - 1];
    }

    @Override
    protected void executeSave(Resume resume, int position) {
        storage[size] = resume;
    }
}
