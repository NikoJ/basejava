package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Collections;
import java.util.List;

public abstract class AbstractStorage implements Storage {

    protected abstract boolean isExist(Object position);

    protected abstract void performUpdate(Resume resume, Object position);

    protected abstract void performSave(Resume resume, Object position);

    protected abstract void performDelete(Object position);

    protected abstract Resume performGet(Object position);

    protected abstract List<Resume> performSorted();

    protected abstract Object getPosition(String uuid);

    @Override
    public void update(Resume resume) {
        Object position = getExistPosition(resume.getUuid());
        performUpdate(resume, position);
    }

    @Override
    public void save(Resume resume) {
        Object position = getNotExistPosition(resume.getUuid());
        performSave(resume, position);
    }

    @Override
    public void delete(String uuid) {
        Object position = getExistPosition(uuid);
        performDelete(position);
    }

    @Override
    public Resume get(String uuid) {
        Object position = getExistPosition(uuid);
        return performGet(position);
    }

    private Object getExistPosition(String uuid) {
        Object position = getPosition(uuid);
        if (!isExist(position)) {
            throw new NotExistStorageException(uuid);
        }
        return position;
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> list = performSorted();
        Collections.sort(list);
        return list;
    }

    private Object getNotExistPosition(String uuid) {
        Object position = getPosition(uuid);
        if (isExist(position)) {
            throw new ExistStorageException(uuid);
        }
        return position;
    }
}
