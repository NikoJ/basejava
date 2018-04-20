package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    public static final int STORAGE_LIMIT = 10000; //fsdfsd

    public Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    /**
     * Equate all values of Resume to null
     */
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    /**
     * @return size array, contains only Resumes in storage (without null)
     */
    public int size() {
        return size;
    }

    /**
     * @param uuid - Unique identifier
     * @return Resume or null
     */
    public Resume get(String uuid) {
        int position = getPosition(uuid);
        if (position != -1) {
            return storage[position];
        } else {
            System.out.println("Resume with uuid=" + uuid + " does not exist");
        }
        return null;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    protected abstract int getPosition(String uuid);
}
