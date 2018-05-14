package com.urise.webapp;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.Storage;

import java.util.*;

public class MainCollections {
    protected static final String UUID_1 = "uuid1";
    protected static final Resume RESUME_1 = new Resume(UUID_1, "");
    protected static final String UUID_2 = "uuid2";
    protected static final Resume RESUME_2 = new Resume(UUID_2, "");
    protected static final String UUID_3 = "uuid3";
    protected static final Resume RESUME_3 = new Resume(UUID_3, "");

    protected Storage storage;

    public static void main(String[] args) {
        ArrayList<Resume> collection = new ArrayList<>();
        collection.add(RESUME_1);
        collection.add(RESUME_2);
        collection.add(RESUME_3);
        collection.trimToSize();

        for (Resume r : collection) {
            //  System.out.println(r);
            //Приу удаление чз этот метод вылетит ConcurrentModificationException
        }

        Iterator<Resume> iterator = collection.listIterator();
        while (iterator.hasNext()) {
            Resume resume = iterator.next();
            System.out.println(resume);
            if (Objects.deepEquals(resume.getUuid(), UUID_1)) {
                iterator.remove();
            }
        }
        System.out.println(collection);

        Map<String, Resume> map = new HashMap<>();
        map.put(UUID_1, RESUME_1);
        map.put(UUID_2, RESUME_2);
        map.put(UUID_3, RESUME_3);

        System.out.println("HashMap");
        for (String uuid : map.keySet()) {
            System.out.println(map.get(uuid));
        }
        System.out.println("HashMap2");
        for (Map.Entry<String, Resume> entry : map.entrySet()) {
            System.out.println(entry.getValue());
        }

        List<Resume> resumes = Arrays.asList(RESUME_1, RESUME_2, RESUME_3);
        System.out.println(resumes);
    }
}
