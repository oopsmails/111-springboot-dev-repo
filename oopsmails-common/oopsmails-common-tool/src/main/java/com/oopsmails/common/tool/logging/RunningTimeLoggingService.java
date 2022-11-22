package com.oopsmails.common.tool.logging;

import com.oopsmails.common.tool.json.JsonUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Slf4j
public class RunningTimeLoggingService {

    public static <T> void consumerAcceptWithLogging(T data, Consumer<T> consumer) {
        long start = System.currentTimeMillis();
        consumer.accept(data);
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

    public static <T, R> R functionApplyWithLogging(T data, Function<T, R> function) {
        long start = System.currentTimeMillis();
        log.info("functionApplyWithLogging, function: [{}], before data: [{}]",
                function,
                JsonUtil.objectToJsonString(data, true));

        R result = function.apply(data);

        log.info("functionApplyWithLogging, do processing after function.apply() for:  result: [{}]",
                JsonUtil.objectToJsonString(result, true));
        long end = System.currentTimeMillis();
        log.info("Elapsed Time in milliseconds: {}", (end - start));
        return result;
    }

    public static <T> T logInfoExecutionTimeOfCallable(Callable<T> task) {
        T call = null;
        try {
            long startTime = System.currentTimeMillis();
            call = task.call();
            log.info("**** Execution Time: {}s ****", (System.currentTimeMillis() - startTime) / 1000d);
        } catch (Exception e) {
            log.warn(e.getMessage());
        }
        return call;
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
}

