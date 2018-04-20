package com.urise.webapp.storage;

import java.util.Arrays;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */

public class ArrayStorage extends AbstractArrayStorage {

    /**
     * @param resume - Resume to be saved
     */
    public void save(Resume resume) {
        String uuid = resume.getUuid();
        int position = getPosition(uuid);
        if (size < STORAGE_LIMIT) {
            if (position == -1) {
                storage[size] = resume;
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
        if (position != -1) {
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
        if (position != -1) {
            size--;
            storage[position] = storage[size];
            storage[size] = null;
        } else {
            System.out.println("Resume with uuid=" + uuid + " does not exist");
        }
    }

    /**
     * if there is a match on the uuid it returns position otherwise -1
     *
     * @param uuid
     * @return position or -1
     */
    @Override
    protected int getPosition(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }
}
