package com.oopsmails.common.tool.logging;

import com.oopsmails.common.tool.json.JsonUtil;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Callable;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Slf4j
public class RunningTimeLoggingService {

    private static final String LOG_EXE_TIME_PREFIX = "**** Elapsed Time in milliseconds: {} ****";

    public static <T> void consumerAcceptWithLogging(T paramT, Consumer<T> consumer) {
        long start = System.currentTimeMillis();
        consumer.accept(paramT);
        long end = System.currentTimeMillis();
        log.info(LOG_EXE_TIME_PREFIX, (end - start));
    }

    public static <T> T supplierGetWithLogging(Supplier<T> supplier) {
        Instant instantStart = Instant.now();
        log.info("supplierGetWithLogging, supplier: [{}]", supplier);
        T result = supplier.get();
        Instant instantEnd = Instant.now();
        log.info(LOG_EXE_TIME_PREFIX, Duration.between(instantStart, instantEnd).toMillis());
        return result;
    }

    public static <T, R> R functionApplyWithLogging(T paramT, Function<T, R> function) {
        Instant instantStart = Instant.now();
        log.info("functionApplyWithLogging, function: [{}], before data: [{}]",
                function,
                JsonUtil.objectToJsonString(paramT, true));

        R result = function.apply(paramT);

        log.info("functionApplyWithLogging, do processing after function.apply() for:  result: [{}]",
                JsonUtil.objectToJsonString(result, true));
        Instant instantEnd = Instant.now();
        log.info(LOG_EXE_TIME_PREFIX, Duration.between(instantStart, instantEnd).toMillis());
        return result;
    }

    public static void logInfoExecutionTimeOfRunnable(Runnable task) {
        try {
            Instant instantStart = Instant.now();
            task.run();
            Instant instantEnd = Instant.now();
            log.info(LOG_EXE_TIME_PREFIX, Duration.between(instantStart, instantEnd).toMillis());
        } catch (Exception e) {
            log.warn(e.getMessage());
        }
    }

    public static <T> T logInfoExecutionTimeOfCallable(Callable<T> task) {
        T result = null;
        try {
            Instant instantStart = Instant.now();
            result = task.call();
            Instant instantEnd = Instant.now();
            log.info(LOG_EXE_TIME_PREFIX, Duration.between(instantStart, instantEnd).toMillis());
        } catch (Exception e) {
            log.warn(e.getMessage());
        }
        return result;
    }

    public static <T, R> R logInfoExecutionTimeOfFunction(T paramT, Function<T, R> function) {
        R result = null;
        try {
            Instant instantStart = Instant.now();
            result = function.apply(paramT);
            Instant instantEnd = Instant.now();
            log.info(LOG_EXE_TIME_PREFIX, Duration.between(instantStart, instantEnd).toMillis());
        } catch (Exception e) {
            log.warn(e.getMessage());
        }
        return result;
    }

    public static <T, U, R> R logInfoExecutionTimeOfBiFunction(T paramT, U paramU, BiFunction<T, U, R> biFunction) {
        R result = null;
        try {
            Instant instantStart = Instant.now();
            result = biFunction.apply(paramT, paramU);
            Instant instantEnd = Instant.now();
            log.info(LOG_EXE_TIME_PREFIX, Duration.between(instantStart, instantEnd).toMillis());
        } catch (Exception e) {
            log.warn(e.getMessage());
        }
        return result;
    }
}

