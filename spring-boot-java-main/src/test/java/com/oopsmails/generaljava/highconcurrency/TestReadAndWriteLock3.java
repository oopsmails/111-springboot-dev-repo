package com.oopsmails.generaljava.highconcurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class TestReadAndWriteLock3 {
    ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public static void main(String[] args) {
        final TestReadAndWriteLock3 lock = new TestReadAndWriteLock3();
        // Build N threads and read at the same time
        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(new Runnable() {
            @Override
            public void run() {
                lock.readFile(Thread.currentThread());
            }
        });
        // Create N threads and write at the same time
        ExecutorService service1 = Executors.newCachedThreadPool();
        service1.execute(new Runnable() {
            @Override
            public void run() {
                lock.writeFile(Thread.currentThread());
            }
        });
    }

    // Read operation
    public void readFile(Thread thread) {
        lock.readLock().lock();
        boolean readLock = lock.isWriteLocked();
        if (!readLock) {
            System.out.println("Currently read lock!");
        }
        try {
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(thread.getName() + ":Reading in progress");
            }
            System.out.println(thread.getName() + ":Read operation finished!");
        } finally {
            System.out.println("Release the read lock!");
            lock.readLock().unlock();
        }
    }

    // Write operation
    public void writeFile(Thread thread) {
        lock.writeLock().lock();
        boolean writeLock = lock.isWriteLocked();
        if (writeLock) {
            System.out.println("Currently write lock!");
        }
        try {
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(thread.getName() + ":Writing in progress");
            }
            System.out.println(thread.getName() + ":Write operation finished!");
        } finally {
            System.out.println("Release the write lock!");
            lock.writeLock().unlock();
        }
    }
}
