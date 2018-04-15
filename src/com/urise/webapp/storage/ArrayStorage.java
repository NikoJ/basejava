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
     * Equate all values of com.urise.webapp.model.Resume to null
     */
    public void clear() {

        Arrays.fill(storage, null);

        size = 0;
    }

    /**
     * @param r - object com.urise.webapp.model.Resume to be saved
     */
    public void save(Resume r) {

        for (int i = 0; i < STORAGE_LENGTH; i++) {
            if (storage[i] == null) {
                storage[i] = r;
                break;
            }
        }
        size++;
    }

    /**
     * @param uuid - Unique identifier in com.urise.webapp.model.Resume
     * @return com.urise.webapp.model.Resume or null
     */
    public Resume get(String uuid) {

        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return storage[i];
            }
        }

        return null;
    }

    /**
     * @param uuid - Unique identifier in com.urise.webapp.model.Resume
     */
    public void delete(String uuid) {

        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                System.arraycopy(storage, i + 1, storage, i, STORAGE_LENGTH - i - 1);
                break;
            }
        }
        size--;
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
}
