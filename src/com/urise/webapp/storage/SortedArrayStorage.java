package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getPosition(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void selectDeletePosition(int position) {
        int selectPosition = position + 1;
        System.arraycopy(storage, selectPosition, storage, position, STORAGE_LIMIT - selectPosition);
        size--;
    }

    @Override
    protected void selectSavePosition(Resume resume, int position) {
        System.out.println("Позиция бин поиска: " + Integer.toString(position));
        int selectPosition = -position - 1;
        System.arraycopy(storage, selectPosition, storage, selectPosition + 1, size - selectPosition);
        storage[selectPosition] = resume;
    }
}
