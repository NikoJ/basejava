package com.urise.webapp;

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
    }

    public enum Singleton{
        INSTANCE
    }
}
