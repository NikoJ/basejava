package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getPosition(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        Arrays.sort(storage, 0, size);
        int temp = Arrays.binarySearch(storage, 0, size, searchKey);
        if (temp >= 0) {
            return temp;
        } else return -1;
    }
}
