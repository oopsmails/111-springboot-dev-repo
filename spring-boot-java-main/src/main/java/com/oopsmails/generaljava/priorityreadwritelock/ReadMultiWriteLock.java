package com.oopsmails.generaljava.priorityreadwritelock;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;

import static java.util.concurrent.Executors.callable;

/**
 * Ref: https://stackoverflow.com/questions/65811954/java-read-write-lock-allowing-multiple-writes
 * <p>
 * In a distributed computer system (not hard-drive related), I have a special scenario:
 * <p>
 * Type A operations -- can happen in parallel
 * Type B operations -- can happen in parallel
 * Type A operations cannot happen in parallel with type B operations
 * Type B operations are very slow -- hence as an optimization I would like them to have higher priority, but this is not strictly necessary
 * As a fast solution I used a read/write lock. But, since the "write" blocks whenever there is another ongoing "write", it is not good enough.
 * <p>
 * Based on
 * http://tutorials.jenkov.com/java-concurrency/read-write-locks.html#simple
 * <p>
 * Allows for multiple writers.
 */

@Slf4j
public class ReadMultiWriteLock {
    private static final ReadMultiWriteLock lock = new ReadMultiWriteLock();
    private int readers = 0;
    private int writers = 0;

    /**
     * Guards specified critical section for reading purposes.
     *
     * @param criticalSection the critical section
     * @return value returned by critical section
     * @throws Throwable if this thread was interrupted or any other exception thrown
     *                   from critical section
     */
    @SneakyThrows
    public static <T> T readLocking(final Callable<T> criticalSection) {
        lock.readingAquire();
        try {
            return criticalSection.call();
        } finally {
            lock.readingRelease();
        }
    }

    /**
     * Guards specified critical section for reading purposes.
     *
     * @param criticalSection the critical section
     * @return always {@code null}
     * @throws Throwable if this thread was interrupted or any other exception thrown
     *                   from critical section
     */
    public static void readLocking(final Runnable criticalSection) {
        readLocking(callable(criticalSection));
    }

    /**
     * Guards specified critical section for writing purposes.
     *
     * @param criticalSection the critical section
     * @return value returned by critical section
     * @throws Throwable if this thread was interrupted or any other exception thrown
     *                   from critical section
     */
    @SneakyThrows
    public static <T> T writeLocking(final Callable<T> criticalSection) {
        lock.writingAcquire();
        try {
            return criticalSection.call();
        } finally {
            lock.writingRelease();
        }
    }

    /**
     * Guards specified critical section for writing purposes.
     *
     * @param criticalSection the critical section
     * @return always {@code null}
     * @throws Throwable if this thread was interrupted or any other exception thrown
     *                   from critical section
     */
    public static void writeLocking(final Runnable criticalSection) {
        writeLocking(callable(criticalSection));
    }

    /**
     * Waits for writers to finish and accounts for another reader lock.
     *
     * @throws InterruptedException if this thread was interrupted
     */
    public synchronized void readingAquire() throws InterruptedException {
        while (writers > 0) {
            log.trace("blocking read -- {} writers running", writers);
            wait();
        }
        readers++;
        log.trace("aquired {} reading locks", readers);
    }

    /**
     * Accounts for one less reader lock.
     */
    public synchronized void readingRelease() {
        readers--;
        notifyAll();
    }

    /**
     * Waits for readers to finish and accounts for another writer lock.
     *
     * @throws InterruptedException if this thread was interrupted
     */
    public synchronized void writingAcquire() throws InterruptedException {
        while (readers > 0) {
            log.trace("blocking write -- {} readers running", readers);
            wait();
        }
        writers++;
        log.trace("aquired {} writing locks", writers);
    }

    /**
     * Accounts for one less writer lock.
     */
    public synchronized void writingRelease() throws InterruptedException {
        writers--;
        notifyAll();
    }
}
