package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public interface Storage {
    /**
     * Equate all values of Resume to null
     */
    public void clear();

    /**
     * @param resume - Resume to be saved
     */
    public void save(Resume resume);

    /**
     * @param resume - Resume to be updated
     */
    public void update(Resume resume);

    /**
     * @param uuid - Unique identifier
     * @return Resume or null
     */
    public Resume get(String uuid);

    /**
     * @param uuid - Unique identifier
     */
    public void delete(String uuid);

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() ;

    /**
     * @return size array, contains only Resumes in storage (without null)
     */
    public int size();
}
