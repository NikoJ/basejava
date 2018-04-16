package com.urise.webapp.storage;

import java.util.Arrays;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */

public class ArrayStorage {

    private int size = 0;
    private final static int STORAGE_LENGTH = 10000;
    private Resume[] storage = new Resume[STORAGE_LENGTH];

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
        if (size < STORAGE_LENGTH) {
            if (position == -1) {
                storage[size] = resume;
                size++;
            } else {
                System.out.println("Resume with uuid=" + uuid + " already exists");
            }
        } else {
            System.out.println("Resume not saved, storage is full");
        }
    }

    /**
     * @param resume - Resume to be updated
     */
    public void update(Resume resume) {
        String uuid = resume.getUuid();
        int position = getPosition(uuid);
        if (position != -1) {
            storage[position] = resume;
        } else {
            System.out.println("Resume with uuid=" + uuid + " does not exist");
        }
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
     * @param uuid - Unique identifier
     */
    public void delete(String uuid) {
        int position = getPosition(uuid);
        if (position != -1) {
            size--;
            storage[position] = storage[size];
            storage[size] = null;
        } else {
            System.out.println("Resume with uuid=" + uuid + " does not exist");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    /**
     * @return size array, contains only Resumes in storage (without null)
     */
    public int size() {
        return size;
    }

    /**
     * if there is a match on the uuid it returns position otherwise -1
     *
     * @param uuid
     * @return position or -1
     */
    private int getPosition(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }
}
