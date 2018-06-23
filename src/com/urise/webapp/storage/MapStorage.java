package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapStorage extends AbstractStorage<String> {
    private Map<String, Resume> map = new HashMap<>();

    @Override
    protected String getPosition(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(String key) {
        return map.containsKey(key);
    }

    @Override
    protected void doUpdate(Resume resume, String key) {
        map.replace(key, resume);
    }

    @Override
    protected void doSave(Resume resume, String key) {
        map.put(resume.getUuid(), resume);
    }

    @Override
    protected void doDelete(String key) {
        map.remove(key);
    }

    @Override
    protected Resume doGet(String key) {
        return map.get(key);
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
