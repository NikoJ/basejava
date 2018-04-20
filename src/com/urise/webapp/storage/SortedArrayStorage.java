package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void save(Resume resume) {
        // SortedArrayStorage хранить элементы отсортированными. Сортировать весь массив не надо)
        String uuid = resume.getUuid();
        int position = getPosition(uuid);
        if (size < STORAGE_LIMIT) {
            if (position == -1) {
                storage[size] = resume;
                size++;
                Arrays.sort(storage, 0, size);
            } else {
                System.out.println("Resume with uuid=" + uuid + " already exists");
            }
        } else {
            System.out.println("Storage overflow");
        }
    }

    @Override
    public void update(Resume resume) {
        String uuid = resume.getUuid();
        int position = getPosition(uuid);
        if (position != -1) {
            storage[position] = resume;
            Arrays.sort(storage, 0, size);
        } else {
            System.out.println("Resume with uuid=" + uuid + " does not exist");
        }
    }

    @Override
    public void delete(String uuid) {
        int position = getPosition(uuid);
        if (position != -1) {
            size--;
            storage[position] = storage[size];
            storage[size] = null;
            Arrays.sort(storage, 0, size);
        } else {
            System.out.println("Resume with uuid=" + uuid + "does not exist");
        }
    }

    @Override
    protected int getPosition(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        int temp = Arrays.binarySearch(storage, 0, size, searchKey);
        if (temp >= 0) {
            return temp;
        } else return -1;
    }
}
