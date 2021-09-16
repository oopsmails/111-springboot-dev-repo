package com.oopsmails.springboot.mockbackend.completablefuture;

import com.oopsmails.springboot.mockbackend.exception.ExceptionUtil;
import com.oopsmails.springboot.mockbackend.exception.OopsException;
import com.oopsmails.springboot.mockbackend.model.general.OopsTimeout;
import com.oopsmails.springboot.mockbackend.utils.GeneralConstants;
import org.slf4j.MDC;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class CompletableFutureUtil {
    public static final CompletableFutureExceptionHandler completableFutureExceptionHandler;

    static {
        completableFutureExceptionHandler = getDefaultExceptionHandler();
    }

    private CompletableFutureUtil() {
    }

    public static CompletableFuture<Void> completableFutureRunAsync(Runnable runnable, ExecutorService executorService) {
        return completableFutureSupplyAsync(() -> {
            runnable.run();
            return null;
        }, executorService);
    }

    public static <T> CompletableFuture<T> completableFutureSupplyAsync(Supplier<T> supplier, ExecutorService executorService) {
        String correlationId = MDC.get(GeneralConstants.MDC_CORRELATION_ID);
        return CompletableFuture.supplyAsync(() -> {
            MDC.put(GeneralConstants.MDC_CORRELATION_ID, correlationId);
            try {
                return supplier.get();
            } finally {
                MDC.remove(GeneralConstants.MDC_CORRELATION_ID);
                MDC.clear();
            }
        }, executorService);
    }

    public static <T> CompletableFuture<List<T>> allOf(List<CompletableFuture<T>> futuresList) {
        CompletableFuture<Void> allFuturesResult =
                CompletableFuture.allOf(futuresList.toArray(new CompletableFuture[futuresList.size()]));
        return allFuturesResult.thenApply(v ->
                futuresList.stream().
                        map(CompletableFuture::join).
                        collect(Collectors.<T>toList())
        );
    }

    private static CompletableFutureExceptionHandler getDefaultExceptionHandler() {
        return exception -> {
            OopsException oopsException = ExceptionUtil.getOopsCause(exception);
            if (oopsException != null) {
                return oopsException;
            }
            String rootCause = ExceptionUtil.getRootCause(exception);
            // some other handler?
            oopsException = new OopsException(rootCause);
            return oopsException;
        };
    }
}
