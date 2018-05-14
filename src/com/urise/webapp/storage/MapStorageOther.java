package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapStorageOther extends AbstractStorage {
    private Map<String, Resume> map = new HashMap<>();

    @Override
    protected Resume getPosition(String uuid) {
        return map.get(uuid);
    }

    @Override
    protected boolean isExist(Object key) {
        return key != null;
    }

    @Override
    protected void performUpdate(Resume resume, Object key) {
        map.replace(resume.getUuid(), resume);
    }

    @Override
    protected void performSave(Resume resume, Object key) {
        map.put(resume.getUuid(), resume);
    }

    @Override
    protected void performDelete(Object key) {
        map.remove(((Resume) key).getUuid());
    }

    @Override
    protected Resume performGet(Object key) {
        return (Resume) key;
    }

    @Override
    protected List<Resume> performSorted() {
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
