package com.urise.webapp;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.*;

import java.util.Arrays;

public class MainTestArrayStorage {
    private static final SortedArrayStorage ARRAY_STORAGE = new SortedArrayStorage();

    //private static final Storage ARRAY_STORAGE2 = new ArrayStorage();
    public static void main(String[] args) {
        Resume r1 = new Resume();
        r1.setUuid("1");
        Resume r2 = new Resume();
        r2.setUuid("2");
        Resume r3 = new Resume();
        r3.setUuid("3");
        Resume r4 = new Resume();
        r4.setUuid("3");

        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);

        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.size());

        System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));

        ARRAY_STORAGE.update(r3); // проверка метода update()
        ARRAY_STORAGE.save(r4); // проверка на совпадение по uuid
        System.out.println("Index: " + Arrays.binarySearch(ARRAY_STORAGE.storage, 0, ARRAY_STORAGE.size(), r1));

        printAll();
        ARRAY_STORAGE.delete(r1.getUuid());
        printAll();
        ARRAY_STORAGE.clear();
        printAll();

        System.out.println("Size: " + ARRAY_STORAGE.size());
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAll()) {
            System.out.println(r);
        }
    }
}
