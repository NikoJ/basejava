package com.urise.webapp;

public class TestSingleton {
    private static TestSingleton instance;

    private TestSingleton() {
    }

    public static TestSingleton getInstance() {
        return instance == null ? instance = new TestSingleton() : instance;
    }
}
