package com.oopsmails.common.tool.logging;

import com.oopsmails.common.tool.json.JsonUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Slf4j
public class RunningTimeLoggingService {

    public static <T> void consumerAcceptWithLogging(T paramT, Consumer<T> consumer) {
        long start = System.currentTimeMillis();
        consumer.accept(paramT);
        long end = System.currentTimeMillis();
        log.info("Elapsed Time in milli seconds: {}", (end - start));
    }

    public static <T> T supplierGetWithLogging(Supplier<T> supplier) {
        long start = System.currentTimeMillis();
        log.info("supplierGetWithLogging, supplier: [{}]", supplier);
        T result = supplier.get();
        long end = System.currentTimeMillis();
        log.info("Elapsed Time in milliseconds: {}", (end - start));
        return result;
    }

    public static <T, R> R functionApplyWithLogging(T paramT, Function<T, R> function) {
        long start = System.currentTimeMillis();
        log.info("functionApplyWithLogging, function: [{}], before data: [{}]",
                function,
                JsonUtil.objectToJsonString(paramT, true));

        R result = function.apply(paramT);

        log.info("functionApplyWithLogging, do processing after function.apply() for:  result: [{}]",
                JsonUtil.objectToJsonString(result, true));
        long end = System.currentTimeMillis();
        log.info("Elapsed Time in milliseconds: {}", (end - start));
        return result;
    }

    public static void logInfoExecutionTimeOfRunnable(Runnable task) {
        try {
            long startTime = System.currentTimeMillis();
            task.run();
            log.info("**** Execution Time: {}s ****", (System.currentTimeMillis() - startTime) / 1000d);
        } catch (Exception e) {
            log.warn(e.getMessage());
        }
    }

    public static <T> T logInfoExecutionTimeOfCallable(Callable<T> task) {
        T result = null;
        try {
            long startTime = System.currentTimeMillis();
            result = task.call();
            log.info("**** Execution Time: {}s ****", (System.currentTimeMillis() - startTime) / 1000d);
        } catch (Exception e) {
            log.warn(e.getMessage());
        }
        return result;
    }

    public static <T, R> R logInfoExecutionTimeOfFunction(T paramT, Function<T, R> function) {
        R result = null;
        try {
            long startTime = System.currentTimeMillis();
            result = function.apply(paramT);
            log.info("**** Execution Time: {}s ****", (System.currentTimeMillis() - startTime) / 1000d);
        } catch (Exception e) {
            log.warn(e.getMessage());
        }
        return result;
    }

    public static <T, U, R> R logInfoExecutionTimeOfBiFunction(T paramT, U paramU, BiFunction<T, U, R> biFunction) {
        R result = null;
        try {
            long startTime = System.currentTimeMillis();
            result = biFunction.apply(paramT, paramU);
            log.info("**** Execution Time: {}s ****", (System.currentTimeMillis() - startTime) / 1000d);
        } catch (Exception e) {
            log.warn(e.getMessage());
        }
        return result;
    }
}

