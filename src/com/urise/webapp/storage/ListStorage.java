package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
    private List<Resume> list = new ArrayList<>();

    @Override
    protected boolean isExist(Integer position) {
        return position != null;
    }

    @Override
    protected void performUpdate(Resume resume, Integer position) {
        list.set(position, resume);
    }

    @Override
    protected void performSave(Resume resume, Integer position) {
        list.add(resume);
    }

    @Override
    protected void performDelete(Integer position) {
        int temp = position;
        list.remove(temp);
    }

    @Override
    protected Resume performGet(Integer position) {
        return list.get(position);
    }

    @Override
    protected List<Resume> performSorted() {
        return new ArrayList<>(list);
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
    public int size() {
        return list.size();
    }
}
