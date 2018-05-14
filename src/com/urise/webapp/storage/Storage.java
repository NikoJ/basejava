package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.List;

public interface Storage {
    /**
     * Equate all values of Resume to null
     */
    void clear();

    /**
     * @param resume - Resume to be saved
     */
    void save(Resume resume);

    /**
     * @param resume - Resume to be updated
     */
    void update(Resume resume);

    /**
     * @param uuid - Unique identifier
     * @return Resume or null
     */
    Resume get(String uuid);

    /**
     * @param uuid - Unique identifier
     */
    void delete(String uuid);

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    List<Resume> getAllSorted();

    /**
     * @return size array, contains only Resumes in storage (without null)
     */
    int size();
}
