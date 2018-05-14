package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {

    private static final Comparator<Resume> RESUME_COMPARATOR = (Comparator.comparing(Resume::getUuid));

    @Override
    protected Integer getPosition(String uuid) {
        Resume searchResume = new Resume(uuid, "");
        return Arrays.binarySearch(storage, 0, size, searchResume, RESUME_COMPARATOR);
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
