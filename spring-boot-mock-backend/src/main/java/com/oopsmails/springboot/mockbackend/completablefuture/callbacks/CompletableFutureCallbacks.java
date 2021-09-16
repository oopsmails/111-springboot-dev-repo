package com.oopsmails.springboot.mockbackend.completablefuture.callbacks;

import com.oopsmails.springboot.mockbackend.completablefuture.CompletableFutureUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

@Slf4j
public class CompletableFutureCallbacks {

    public static void main(String[] args) {
        CompletableFutureCallbacks completableFutureCallbacks = new CompletableFutureCallbacks();
        ExecutorService executorService = CompletableFutureUtil.executorService;

        try {
            String userName = "Albert";
            String runSyncThenApplyResult = completableFutureCallbacks.runThenApply(executorService, userName);
            log.info("main() done: runSyncThenApplyResult = [{}]", runSyncThenApplyResult);
            completableFutureCallbacks.runThenAccept(executorService, userName);
            completableFutureCallbacks.runThenApplyAsync(executorService, userName);
            completableFutureCallbacks.runThenRun(executorService, userName);

            log.info("main() done .... back in main thread.");
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        executorService.shutdown();
    }

    public String runThenApply(Executor executor, String userName) throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = constructThenApply(executor, userName);
        log.info("in runThenApply .... for checking in which thread.");
        return completableFuture.get();
    }

    public void runThenAccept(Executor executor, String userName) throws ExecutionException, InterruptedException {
        CompletableFuture<Void> completableFuture = constructThenAccept(executor, userName);
        log.info("in runThenAccept .... for checking in which thread.");
        completableFuture.get();
    }

    public String runThenApplyAsync(Executor executor, String userName) throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = constructThenApplyAsync(executor, userName);
        log.info("in runThenApplyAsync .... for checking in which thread.");
        return completableFuture.get();
    }

    public void runThenRun(Executor executor, String userName) throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = constructThenApply(executor, userName);
        CompletableFuture<Void> thenRunFuture = completableFuture.thenRun(() -> log.info("Applying thenRun() ..."));
        thenRunFuture.get();
    }


    public CompletableFuture<String> constructThenApply(Executor executor, String userName) throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            log.info("constructThenApply: run in a separate thread than the main thread, returning {}", userName);
            return userName;
        }, executor).thenApply(name -> {
            String result = "Hello " + name;
            log.info("constructThenApply: [{}]", result);
            return result;
        }).thenApply(greeting -> {
            String result = greeting + ", thanks!";
            log.info("constructThenApply: [{}]", result);
            return result;
        });

        return completableFuture;
    }

    public CompletableFuture<Void> constructThenAccept(Executor executor, String userName) throws ExecutionException, InterruptedException {
        CompletableFuture<Void> completableFuture = CompletableFuture.supplyAsync(() -> {
            log.info("constructThenAccept: run in a separate thread than the main thread, returning {}", userName);
            return userName;
        }, executor).thenAccept(name -> {
            String result = "Hello " + name;
            log.info("constructThenAccept: [{}]", result);
        });

        return completableFuture;
    }

    public CompletableFuture<String> constructThenApplyAsync(Executor executor, String userName) throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            log.info("constructThenApplyAsync: run in a separate thread than the main thread, returning {}", userName);
            return userName;
        }, executor).thenApplyAsync(name -> {
            String result = "Hello " + name;
            log.info("constructThenApplyAsync: [{}]", result);
            return result;
        }, executor).thenApplyAsync(greeting -> {
            String result = greeting + ", thanks!";
            log.info("constructThenApplyAsync: [{}]", result);
            return result;
        });

        return completableFuture;
    }
}
