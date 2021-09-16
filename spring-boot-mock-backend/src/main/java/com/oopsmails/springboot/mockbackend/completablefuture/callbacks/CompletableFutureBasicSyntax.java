package com.oopsmails.springboot.mockbackend.completablefuture.callbacks;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class CompletableFutureBasicSyntax {
    // CompletableFuture executes these tasks in a thread obtained from the global ForkJoinPool.commonPool().
    // To use a custom pool, see AsyncConfig
    // !!!! Note: if using this executor, then the main() method will not end, because executor will NOT shut down automatically !!!!
    public static ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {
        CompletableFutureBasicSyntax completableFutureBasicSyntax = new CompletableFutureBasicSyntax();

        try {
            completableFutureBasicSyntax.runAsyncRunnable(executorService);
            completableFutureBasicSyntax.runAsyncLambda(executorService);
            completableFutureBasicSyntax.runSupplyAsync(executorService);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        executorService.shutdown();
    }

    public void runAsyncLambda(Executor executor) throws ExecutionException, InterruptedException {
        // Run a task specified by a Runnable Object asynchronously.
        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
            // Simulate a long-running Job
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            log.warn("runAsyncLambda: run in a separate thread than the main thread.");
        }, executor);

        completableFuture.get();
    }

    public void runSupplyAsync(Executor executor) throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return "Async exec and return";
        }, executor);

        String result = completableFuture.get();
        log.warn("runSupplyAsync: run in a separate thread than the main thread, result = [{}]", result);
    }


    public void runAsyncRunnable(Executor executor) throws ExecutionException, InterruptedException {
        // Run a task specified by a Runnable Object asynchronously.
        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                // Simulate a long-running Job
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new IllegalStateException(e);
                }
                log.warn("runAsyncRunnable: run in a separate thread than the main thread.");
            }
        }, executor).exceptionally(ex -> {
                    log.error("Something went wrong : ", ex);
                    return null;
                }
        );
        completableFuture.get();
    }
}
