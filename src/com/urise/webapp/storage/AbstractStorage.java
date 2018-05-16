package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<P> implements Storage {
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    protected abstract boolean isExist(P position);

    protected abstract void performUpdate(Resume resume, P position);

    protected abstract void performSave(Resume resume, P position);

    protected abstract void performDelete(P position);

    protected abstract Resume performGet(P position);

    protected abstract List<Resume> performSorted();

    protected abstract P getPosition(String uuid);

    @Override
    public void update(Resume resume) {
        LOG.info("Update " + resume);
        P position = getExistPosition(resume.getUuid());
        performUpdate(resume, position);
    }

    @Override
    public void save(Resume resume) {
        LOG.info("Save " + resume);
        P position = getNotExistPosition(resume.getUuid());
        performSave(resume, position);
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        P position = getExistPosition(uuid);
        performDelete(position);
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        P position = getExistPosition(uuid);
        return performGet(position);
    }

    private P getExistPosition(String uuid) {
        P position = getPosition(uuid);
        if (!isExist(position)) {
            LOG.warning("Resume " + uuid + " not exist");
            throw new NotExistStorageException(uuid);
        }
        return position;
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("GetAllSorted");
        List<Resume> list = performSorted();
        Collections.sort(list);
        return list;
    }

    private P getNotExistPosition(String uuid) {
        P position = getPosition(uuid);
        if (isExist(position)) {
            LOG.warning("Resume " + uuid + " already exist");
            throw new ExistStorageException(uuid);
        }
        return position;
    }
}
