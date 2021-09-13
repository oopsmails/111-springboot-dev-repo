package com.oopsmails.springboot.mockbackend.async;

import com.oopsmails.springboot.mockbackend.exception.ExceptionUtil;
import com.oopsmails.springboot.mockbackend.exception.OopsException;
import com.oopsmails.springboot.mockbackend.model.general.OopsTimeout;
import com.oopsmails.springboot.mockbackend.utils.GeneralConstants;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;

@Slf4j
public final class OperationTaskUtil {
    public static OperationExceptionHandler operationExceptionHandler;

    static {
        operationExceptionHandler = getDefaultExceptionHandler();
    }

    private OperationTaskUtil() {
    }

    public static void executeOperation(OperationContext operationContext, ExecutorService executorService, OopsTimeout oopsTimeout, boolean isToRunInParallel) throws OopsException {
        executeOperation(operationContext, executorService, oopsTimeout, isToRunInParallel, operationExceptionHandler);
    }

    public static void executeOperation(OperationContext operationContext, ExecutorService executorService, OopsTimeout oopsTimeout, boolean isToRunInParallel, OperationExceptionHandler operationExceptionHandlerPassedIn) throws OopsException {
        if (operationContext == null || operationContext.getOperationTasks().isEmpty()) {
            log.warn("Should not be here, already checked in OperationService, No task to execute ... ");
            return;
        }

        final OperationExceptionHandler exceptionHandler = operationExceptionHandlerPassedIn == null ? operationExceptionHandler : operationExceptionHandlerPassedIn;
        if (isToRunInParallel) {
            executeInParallel(operationContext, exceptionHandler, oopsTimeout, executorService);
        } else {
            executeSequentially(operationContext, exceptionHandler);
        }
    }

    private static void executeInParallel(OperationContext operationContext, OperationExceptionHandler exceptionHandler, OopsTimeout oopsTimeout, ExecutorService executorService) {
        log.info("Running tasks in parallel ...");

        List<CompletableFuture<Void>> futureList = new ArrayList<>();
        for (final OperationTask operationTask : operationContext.getOperationTasks()) {
            Runnable runnable = () -> operationTask.getOperationDataLoader().loadData(operationTask.getOperationTaskContext());
            CompletableFuture<Void> future = OperationTaskUtil.wrappedCompletableFutureVoid(runnable, executorService);
            future.exceptionally(exception -> {
                throw exceptionHandler.handle(exception);
            });
            futureList.add(future);
        }

        CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(futureList.toArray(new CompletableFuture[futureList.size()]));
        try {
            if (oopsTimeout == null) {
                combinedFuture.get();
            } else {
                combinedFuture.get(oopsTimeout.getTimeout(), oopsTimeout.getTimeUnit());
            }
            log.info("CompletableFuture combinedFuture done successfully, size = [{}] ...", futureList.size());
        } catch (InterruptedException | TimeoutException | ExecutionException ex) {
            log.error("Cannot complete the operations.", ex);
            throw exceptionHandler.handle(ex);
        }
    }

    private static void executeSequentially(OperationContext operationContext, OperationExceptionHandler exceptionHandler) {
        log.info("Running tasks sequentially ...");
        for (final OperationTask operationTask : operationContext.getOperationTasks()) {
            OperationTaskContext operationTaskContext = operationTask.getOperationTaskContext();
            operationTask.getOperationDataLoader().loadData(operationTaskContext);
//            operationTask.getOperationTaskContext().setTaskOutput(operationTask.getOperationTaskContext());
        }
    }

    public static CompletableFuture<Void> wrappedCompletableFutureVoid(Runnable runnable, ExecutorService executorService) {
        return wrappedCompletableFuture(() -> {
            runnable.run();
            return null;
        }, executorService);
    }

    public static <T> CompletableFuture<T> wrappedCompletableFuture(Supplier<T> supplier, ExecutorService executorService) {
        String correlationId = MDC.get(GeneralConstants.MDC_CORRELATION_ID);
        return CompletableFuture.supplyAsync(() -> {
            MDC.put(GeneralConstants.MDC_CORRELATION_ID, correlationId);

            try {
                return supplier.get();
            } finally {
                MDC.remove(GeneralConstants.MDC_CORRELATION_ID);
                MDC.clear();
            }
        });
    }

    private static OperationExceptionHandler getDefaultExceptionHandler() {
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
