package com.oopsmails.springboot.mockbackend.completablefuture.greeting;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

@Component
@Slf4j
public class GreetingServiceRunner {
    @Autowired
    private Executor generalTaskExecutor;

    @Autowired
    private GreetingService greetingService;

    public List<CompletableFuture<GreetHolder>> runnerGetAllGreetingsSyncExceptionHandling(List<String> langList) {
        List<CompletableFuture<GreetHolder>> completableFutures = langList
                .stream()
                .map(lang -> runnerGetGreetingSyncExceptionHandling(lang))
                .collect(Collectors.toList());
        return completableFutures;
    }

    public List<CompletableFuture<GreetHolder>> runnerGetAllGreetingsAsyncExceptionHandling(List<String> langList) {
        List<CompletableFuture<GreetHolder>> completableFutures = langList
                .stream()
                .map(lang -> runnerGetGreetingAsyncExceptionHandling(lang))
                .collect(Collectors.toList());
        return completableFutures;
    }

    public List<String> runnerGetAllGreetings(List<String> langList, boolean isAsyncErrorHandling) {
        List<String> result = new ArrayList<>();

        try {
            List<CompletableFuture<GreetHolder>> completableFutures = isAsyncErrorHandling ?
                    runnerGetAllGreetingsAsyncExceptionHandling(langList) : runnerGetAllGreetingsSyncExceptionHandling(langList);

            CompletableFuture<Void> allFutures = CompletableFuture
                    .allOf(completableFutures.toArray(new CompletableFuture[completableFutures.size()]));

            CompletableFuture<List<GreetHolder>> allCompletableFuture = allFutures.thenApply(future -> {
                return completableFutures.stream()
                        .map(completableFuture -> completableFuture.join())
                        .collect(Collectors.toList());
            });

            CompletableFuture<List<String>> completableFuture = allCompletableFuture.thenApply(greets -> {
                return greets.stream().map(GreetHolder::getGreet).collect(Collectors.toList());
            });

            result = completableFuture.get(5, TimeUnit.MINUTES).stream().filter(Objects::nonNull).collect(Collectors.toList());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return result;
    }

    public CompletableFuture<GreetHolder> runnerGetGreetingSyncExceptionHandling(String lang) {
        return CompletableFuture.supplyAsync(() -> {
            try {
//                log.info("Task execution started.");
//                Thread.sleep(2000);
                GreetHolder greetHolder = new GreetHolder(greetingService.getGreet(lang));
//                log.info("Task execution stopped.");
                return greetHolder;
            } catch (InterruptedException e) {
                log.warn("InterruptedException, e = {}", e);
                return null;
            }
        }, generalTaskExecutor);
    }

    public CompletableFuture<GreetHolder> runnerGetGreetingAsyncExceptionHandling(String lang) {
        return CompletableFuture.supplyAsync(() -> {
            try {
//                log.info("Task execution started.");
//                Thread.sleep(2000);
                GreetHolder greetHolder = new GreetHolder(greetingService.getGreet(lang));
//                log.info("Task execution stopped.");
                return greetHolder;
            } catch (InterruptedException e) {
                log.warn("InterruptedException, e = {}", e);
                return null;
            }
        }, generalTaskExecutor).exceptionally(ex -> {
                    log.error("Something went wrong : ", ex);
                    return null;
                }
        );
    }
}
