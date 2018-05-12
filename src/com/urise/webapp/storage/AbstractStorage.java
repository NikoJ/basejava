package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    protected abstract boolean isExist(Object position);

    protected abstract void performUpdate(Resume resume, Object position);

    protected abstract void performSave(Resume resume, Object position);

    protected abstract void performDelete(Object position);

    protected abstract Resume performGet(Object position);

    protected abstract Object getPosition(String uuid);

    public void update(Resume resume) {
        Object position = getPosition(resume.getUuid());
        if (isExist(position)) {
            performUpdate(resume, position);
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    public void save(Resume resume) {
        Object position = getPosition(resume.getUuid());
        if (!isExist(position)) {
            performSave(resume, position);
        } else {
            throw new ExistStorageException(resume.getUuid());
        }
    }

    public void delete(String uuid) {
        Object position = getPosition(uuid);
        if (isExist(position)) {
            performDelete(position);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    public Resume get(String uuid) {
        Object position = getPosition(uuid);
        if (isExist(position)) {
            return performGet(position);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }
}
