package com.urise.webapp;

import com.urise.webapp.model.SectionType;

public class TestSingleton {
    private static TestSingleton instance;

    private TestSingleton() {
    }

    public static TestSingleton getInstance() {
        return instance == null ? instance = new TestSingleton() : instance;
    }

    public static void main(String[] args) {
        TestSingleton.getInstance().toString();
        Singleton singleton = Singleton.valueOf("INSTANCE");
        System.out.println(singleton.ordinal());
        for (SectionType type : SectionType.values()) {
            System.out.println(type.getTitle());
        }
    }

    public enum Singleton {
        INSTANCE
    }
}
