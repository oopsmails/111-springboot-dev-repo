package com.oopsmails.springboot.javamain.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainTestThreadLocalSimpleDateFormat implements Runnable {
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static ThreadLocal<SimpleDateFormat> inDateFormatHolder =
            ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd"));

    public static void main(String[] args) {
        MainTestThreadLocalSimpleDateFormat td = new MainTestThreadLocalSimpleDateFormat();
        Thread t1 = new Thread(td, "Thread-1");
        Thread t2 = new Thread(td, "Thread-2");
        t1.start();
        t2.start();
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {

            System.out.println("Thread run execution started for " + Thread.currentThread().getName());
//            System.out.println("Date formatter pattern is  " + simpleDateFormat.toPattern());
            System.out.println("Date formatter pattern is  " + inDateFormatHolder.get().toPattern());

            try {
                // java.lang.NumberFormatException: multiple points
//                System.out.println("Formatted date is " + simpleDateFormat.parse("2019-05-24 06:02:20"));
                System.out.println("Parsed date string is " + inDateFormatHolder.get().parse("2019-05-24 06:02:20"));
                System.out.println("Formatted date is " + inDateFormatHolder.get().format(new Date()));
            } catch (ParseException pe) {
                pe.printStackTrace();
            }

            System.out.println("=========================================================");
        }
    }
}
