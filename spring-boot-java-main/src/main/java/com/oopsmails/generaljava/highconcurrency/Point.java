package com.oopsmails.generaljava.highconcurrency;

import java.util.concurrent.locks.StampedLock;

public class Point {
    private final StampedLock stampedLock = new StampedLock();

    private double x;
    private double y;

    public void move(double deltaX, double deltaY) {
        long stamp = stampedLock.writeLock(); // Get write lock
        try {
            x += deltaX;
            y += deltaY;
        } finally {
            stampedLock.unlockWrite(stamp); // Release write lock
        }
    }

    public double distanceFromOrigin() {
        long stamp = stampedLock.tryOptimisticRead(); // Get an optimistic read lock
        // Note that the following two lines are not atomic operations
        // Suppose x, y = (100200)
        double currentX = x;
        // x=100 has been read here, but x,y may be modified to (300400) by the write thread
        double currentY = y;
        // y has been read here, if not written, the read is correct (100200)
        // If there is a write, the read is wrong (100400)
        if (!stampedLock.validate(stamp)) { // Check whether there are other write locks after optimistic read lock
            stamp = stampedLock.readLock(); // Get a pessimistic read lock
            try {
                currentX = x;
                currentY = y;
            } finally {
                stampedLock.unlockRead(stamp); // Release pessimistic read lock
            }
        }
        return Math.sqrt(currentX * currentX + currentY * currentY);
    }
}
