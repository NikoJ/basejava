package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Map;
import java.util.TreeMap;

public class MapStorage extends AbstractStorage {
    private Map<String, Resume> map = new TreeMap<>();

    @Override
    protected boolean isExist(Object position) {
        return map.containsKey(position);
    }

    @Override
    protected void performUpdate(Resume resume, Object position) {
        map.replace((String) position, resume);
    }

    @Override
    protected void performSave(Resume resume, Object position) {
        map.put(resume.getUuid(), resume);
    }

    @Override
    protected void performDelete(Object position) {
        map.remove(position);
    }

    @Override
    protected Resume performGet(Object position) {
        return map.get(position);
    }

    @Override
    protected String getPosition(String uuid) {
        return uuid;
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Resume[] getAll() {
        return map.values().toArray(new Resume[size()]);
    }

    @Override
    public int size() {
        return map.size();
    }
}
