package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private List<Resume> list = new ArrayList<>();

    @Override
    protected boolean isExist(Object position) {
        return position != null;
    }

    @Override
    protected void performUpdate(Resume resume, Object position) {
        list.set((Integer) position, resume);
    }

    @Override
    protected void performSave(Resume resume, Object position) {
        list.add(resume);
    }

    @Override
    protected void performDelete(Object position) {
        int temp = (Integer) position;
        list.remove(temp);
    }

    @Override
    protected Resume performGet(Object position) {
        return list.get((Integer) position);
    }

    @Override
    protected Integer getPosition(String uuid) {
        for (int i = 0; i < list.size(); i++) {
            if (uuid.equals(list.get(i).getUuid())) {
                return i;
            }
        }
        return null;
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public Resume[] getAll() {
        return list.toArray(new Resume[size()]);
    }

    @Override
    public int size() {
        return list.size();
    }
}
