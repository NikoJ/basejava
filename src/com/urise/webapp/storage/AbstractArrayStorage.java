package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    /**
     * Equate all values of Resume to null
     */
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    /**
     * @param resume - Resume to be saved
     */
    public void save(Resume resume) {
        String uuid = resume.getUuid();
        int position = getPosition(uuid);
        if (size < STORAGE_LIMIT) {
            if (position < 0) {
                executeSavePosition(resume, position);
                size++;
            } else {
                System.out.println("Resume with uuid=" + uuid + " already exists");
            }
        } else {
            System.out.println("Storage overflow");
        }
    }

    /**
     * @param resume - Resume to be updated
     */
    public void update(Resume resume) {
        String uuid = resume.getUuid();
        int position = getPosition(uuid);
        if (position >= 0) {
            storage[position] = resume;
        } else {
            System.out.println("Resume with uuid=" + uuid + " does not exist");
        }
    }

    /**
     * @param uuid - Unique identifier
     */
    public void delete(String uuid) {
        int position = getPosition(uuid);
        if (position >= 0) {
            executeDeletePosition(position);
            storage[size] = null;
            size--;
        } else {
            System.out.println("Resume with uuid=" + uuid + " does not exist");
        }
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
        if (position >= 0) {
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

    /**
     * if there is a match on the uuid it returns position otherwise -1
     *
     * @param uuid
     * @return position or -1
     */
    protected abstract int getPosition(String uuid);

    protected abstract void executeDeletePosition(int position);

    protected abstract void executeSavePosition(Resume resume, int position);
}
