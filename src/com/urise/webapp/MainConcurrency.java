package com.urise.webapp;

import com.urise.webapp.util.LazySingleton;

import java.util.ArrayList;
import java.util.List;

public class MainConcurrency {
    public static final Object LOCK = new Object();
    public static final int THREADS_NUMBER = 10000;
    public static volatile int count;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("______________________Main_thread______________________");
        System.out.println("Name: " + Thread.currentThread().getName());
        System.out.println("Id: " + Thread.currentThread().getId());
        System.out.println("Priority: " + Thread.currentThread().getPriority());
        System.out.println("State: " + Thread.currentThread().getState());
        new Thread(() -> {
            System.out.println("______________________Two_thread______________________");
            System.out.println("Name: " + Thread.currentThread().getName());
            System.out.println("Id: " + Thread.currentThread().getId());
            System.out.println("Priority: " + Thread.currentThread().getPriority());
            System.out.println("State: " + Thread.currentThread().getState());
        }).start();

        final MainConcurrency mainConcurrency = new MainConcurrency();
        List<Thread> threads = new ArrayList<>(THREADS_NUMBER);
        for (int i = 0; i < THREADS_NUMBER; i++) {
            Thread thread = new Thread(() -> {

                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc();
                    System.out.println(LazySingleton.getInstance());
                }
            });
            thread.start();
            threads.add(thread);
//            thread.join();
        }
        Thread.sleep(1000);
        threads.forEach((t) -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(count);
    }

    private synchronized void inc() {
//        double a = Math.sin(13.);
//        synchronized (this) {
        count++;
//        }
    }
}
