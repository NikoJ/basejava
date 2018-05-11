package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getPosition(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void executeDelete(int position) {
        int selectPosition = position + 1;
        System.arraycopy(storage, selectPosition, storage, position, STORAGE_LIMIT - selectPosition);
    }

    @Override
    protected void executeSave(Resume resume, int position) {
        int selectPosition = -position - 1;
        System.arraycopy(storage, selectPosition, storage, selectPosition + 1, size - selectPosition);
        storage[selectPosition] = resume;
    }
}
