package com.oopsmails.generaljava.highconcurrency;

public class TestReadAndWriteLock1 {
    public static void main(String[] args) {
        final TestReadAndWriteLock1 lock = new TestReadAndWriteLock1();
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

    public synchronized void get(Thread thread) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(thread.getName() + ":Reading in progress");
        }
        System.out.println(thread.getName() + ":Read operation finished!");
        long end = System.currentTimeMillis();
        System.out.println("Time:" + (end - start) + "ms");
    }
}
