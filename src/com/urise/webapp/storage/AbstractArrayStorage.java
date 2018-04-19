package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

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

    protected abstract int getPosition(String uuid);
}
