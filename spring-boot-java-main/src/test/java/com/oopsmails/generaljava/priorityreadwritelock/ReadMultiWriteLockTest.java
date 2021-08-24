package com.oopsmails.generaljava.priorityreadwritelock;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.Test;

import java.util.concurrent.ThreadLocalRandom;

import static com.oopsmails.generaljava.priorityreadwritelock.ReadMultiWriteLock.readLocking;
import static com.oopsmails.generaljava.priorityreadwritelock.ReadMultiWriteLock.writeLocking;
import static java.lang.Thread.sleep;

@Slf4j
public class ReadMultiWriteLockTest {
    private static final ParallelLoop PARALLEL_LOOP = ParallelLoop.INSTANCE.withParallelism(100)
            // NOTE: when running with trace may take long time
            .withRepetitions(200_000);

    @Test
    public void shouldExclusivlyLockForReadsAndWrites() {
        val sharedState = new State();
        PARALLEL_LOOP.run(id -> State.randomReadWrite() ? writeLocking(() -> sharedState.write(id)) : readLocking(() -> sharedState.read(id)));
    }

    @Test(expected = IllegalStateException.class)
    public void shouldFailIfNeitherLocked() {
        val sharedState = new State();
        PARALLEL_LOOP.run(id -> State.randomReadWrite() ? sharedState.write(id) : sharedState.read(id));
    }

    @Test(expected = IllegalStateException.class)
    public void shouldFailIfNotReadLocked() {
        val sharedState = new State();
        PARALLEL_LOOP.run(id -> State.randomReadWrite() ? writeLocking(() -> sharedState.write(id)) : sharedState.read(id));
    }

    @Test(expected = IllegalStateException.class)
    public void shouldFailIfNotWriteLocked() {
        val sharedState = new State();
        PARALLEL_LOOP.run(id -> State.randomReadWrite() ? sharedState.write(id) : readLocking(() -> sharedState.read(id)));
    }

    @Test(expected = RuntimeException.class)
    public void shouldExecuteReadingCriticalSectionWithoutValue() {
        readLocking((Runnable) () -> {
            throw new RuntimeException("reading critical section executed");
        });
    }

    @Test(expected = RuntimeException.class)
    public void shouldExecuteWritingCriticalSectionWithoutValue() {
        writeLocking((Runnable) () -> {
            throw new RuntimeException("writing critical section executed");
        });
    }

    private static final class State {
        private static final int MAX_DELAY_MS = 10;
        private Status readStatus, writeStatus;

        static int randomDelay(final int maxMs) {
            return ThreadLocalRandom.current().nextInt(0, maxMs);
        }

        static boolean randomReadWrite() {
            return ThreadLocalRandom.current().nextBoolean();
        }

        @SneakyThrows
        int read(final int id) {
            if (Status.STARTED == writeStatus) {
                throw new IllegalStateException("other thread is writing");
            }
            readStatus = Status.STARTED;
            log.trace(">>> start reading {}", id);
            sleep(randomDelay(MAX_DELAY_MS));
            log.trace("<<< end reading {}", id);
            readStatus = Status.ENDED;
            return id;
        }

        @SneakyThrows
        int write(final int id) {
            if (Status.STARTED == readStatus) {
                throw new IllegalStateException("other thread is reading");
            }
            writeStatus = Status.STARTED;
            log.trace(">>> start writing {}", id);
            sleep(randomDelay(MAX_DELAY_MS));
            log.trace("<<< end writing {}", id);
            writeStatus = Status.ENDED;
            return id;
        }

        private enum Status {
            STARTED, ENDED
        }
    }
}
