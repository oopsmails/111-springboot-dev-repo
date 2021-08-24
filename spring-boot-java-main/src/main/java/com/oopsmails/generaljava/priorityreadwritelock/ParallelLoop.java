package com.oopsmails.generaljava.priorityreadwritelock;

import lombok.AllArgsConstructor;
import lombok.With;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

/**
 * Parallel looping with specified threads, repetitions and block of code.
 */
@Slf4j
@AllArgsConstructor
public class ParallelLoop {
    /**
     * The default parallel loop; chain with {@link #parallelism} and
     * {@link #repetitions} to configure.
     */
    public static final ParallelLoop INSTANCE = new ParallelLoop();
    @With
    private final int parallelism;
    @With
    private final int repetitions;

    /**
     * Constructs a default parallel loop with one thread and one repetition.
     */
    public ParallelLoop() {
        parallelism = 1;
        repetitions = 1;
    }

    /**
     * Runs specified function in configured loop.
     *
     * @param function the function to run; is called with the run identifier and
     *                 expected to return it
     */
    public void run(final Function<Integer, Integer> function) {
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", String.valueOf(parallelism));
        IntStream.range(0, repetitions).parallel().forEach(id -> log.trace("run id {}", function.apply(id)));
    }

    /**
     * Runs specified consumer in configure loop.
     *
     * @param consumer the consumer to run; is called with the run identifier.
     */
    public void run(final IntConsumer consumer) {
        run(id -> {
            consumer.accept(id);
            return id;
        });
    }
}
