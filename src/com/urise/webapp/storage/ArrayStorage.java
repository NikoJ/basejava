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
        Arrays.fill(storage, null);
        size = 0;
    }

    /**
     * @param r - Resume to be saved
     */
    public void save(Resume r) {
        for (int i = 0; i < STORAGE_LENGTH; i++) {
            if (storage[i] == null && !checkResume(r)) {
                storage[i] = r;
                size++;
                break;
            }
        }
    }

    /**
     * @param resume - Resume to be updated
     */
    public void update(Resume resume) {
        for (int i = 0; i < STORAGE_LENGTH; i++) {
            if (checkResume(resume)) {
                System.out.println("Update: " + resume.getUuid());
                break;
            }
        }
    }

    /**
     * @param uuid - Unique identifier
     * @return Resume or null
     */
    public Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (checkUuid(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    /**
     * @param uuid - Unique identifier
     */
    public void delete(String uuid) {
        for (int i = 0; i < size; i++) {
            if (checkUuid(uuid)) {
                System.arraycopy(storage, i + 1, storage, i, STORAGE_LENGTH - i - 1);
                size--;
                break;
            }
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
     * if there is a match on the Resume then returns true otherwise false
     * @param resume
     * @return true or false
     */
    private boolean checkResume(Resume resume) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(resume.getUuid())) {
                return true;
            }
        }
        return false;
    }

    /**
     * if there is a match on the uuid it returns true otherwise false
     * @param uuid
     * @return true or false
     */
    private boolean checkUuid(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return true;
            }
        }
        return false;
    }
}
