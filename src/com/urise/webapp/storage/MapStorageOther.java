package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapStorageOther extends AbstractStorage<Resume> {
    private Map<String, Resume> map = new HashMap<>();

    @Override
    protected Resume getPosition(String uuid) {
        return map.get(uuid);
    }

    @Override
    protected boolean isExist(Resume key) {
        return key != null;
    }

    @Override
    protected void doUpdate(Resume resume, Resume key) {
        map.replace(resume.getUuid(), resume);
    }

    @Override
    protected void doSave(Resume resume, Resume key) {
        map.put(resume.getUuid(), resume);
    }

    @Override
    protected void doDelete(Resume key) {
        map.remove(key.getUuid());
    }

    @Override
    protected Resume doGet(Resume key) {
        return key;
    }

    @Override
    protected List<Resume> doCopyAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public int size() {
        return map.size();
    }
}
