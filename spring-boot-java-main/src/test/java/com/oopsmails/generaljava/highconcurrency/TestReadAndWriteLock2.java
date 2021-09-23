package com.oopsmails.generaljava.highconcurrency;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class TestReadAndWriteLock2 {
    ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public static void main(String[] args) {
        final TestReadAndWriteLock2 lock = new TestReadAndWriteLock2();
        new Thread(new Runnable() {
            @Override
            public void run() {
                lock.get(Thread.currentThread());
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                lock.get(Thread.currentThread());
            }
        }).start();
    }

    public void get(Thread thread) {
        lock.readLock().lock();
        try {
            System.out.println("start time:" + System.currentTimeMillis());
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(thread.getName() + ":Reading in progress");
            }
            System.out.println(thread.getName() + ":Read operation finished!");
            System.out.println("end time:" + System.currentTimeMillis());
        } finally {
            lock.readLock().unlock();
        }
    }
}
