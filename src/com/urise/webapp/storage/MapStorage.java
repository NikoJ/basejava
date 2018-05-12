package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Map;
import java.util.TreeMap;

public class MapStorage extends AbstractStorage {
    private Map<String, Resume> map = new TreeMap<>();

    @Override
    protected boolean isExist(Object position) {
        return position != null;
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
        for (Map.Entry<String, Resume> item : map.entrySet()) {
            if (uuid.equals(item.getValue().getUuid())) {
                return item.getKey();
            }
        }
        return null;
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
