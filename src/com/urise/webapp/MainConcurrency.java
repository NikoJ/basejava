package com.urise.webapp;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainConcurrency {
    //    public static final Object LOCK = new Object();
    public static final int THREADS_NUMBER = 10000;
    public static final Lock lock = new ReentrantLock();
    public static volatile int count;
    private final AtomicInteger atomicInteger = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException, ExecutionException {
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
        CountDownLatch latch = new CountDownLatch(THREADS_NUMBER);
        ExecutorService executorService = Executors.newCachedThreadPool();

//        List<Thread> threads = new ArrayList<>(THREADS_NUMBER);
        for (int i = 0; i < THREADS_NUMBER; i++) {
            Future<Integer> future = executorService.submit(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc();
                }
                latch.countDown();
                return 5;
            });
//            System.out.println(future.isDone());
//            System.out.println(future.get());
/*            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc();
                }
                latch.countDown();
            });
            thread.start();
 */
//            threads.add(thread);
//            thread.join();
        }

 /*       threads.forEach((t) -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
 */
        latch.await();
        executorService.shutdown();
        System.out.println(count);
        System.out.println(mainConcurrency.atomicInteger.get());
    }

    private void inc() {
//        double a = Math.sin(13.);
//        synchronized (this) {
//        lock.lock();
//        try {
        atomicInteger.incrementAndGet();
//            count++;
//        } finally {
//            lock.unlock();
//        }
//        }
    }
}
