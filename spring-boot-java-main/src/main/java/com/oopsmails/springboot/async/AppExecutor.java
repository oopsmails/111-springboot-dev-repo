package com.oopsmails.springboot.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Future;

@Component
@Slf4j
public class AppExecutor {

    @Async("concurrentTaskExcutor")
    public void runWithConcurrentExecutor(List<String> taskList) {
        for (String task : taskList) {
            log.info("1. concurrentTaskExcutor thread name - {}", Thread.currentThread().getName());
            log.info("Task is: " + task);
        }
    }

    @Async("executorAsync")
    public void runWithExecutorAsync(String task) {
        log.info("2. executorAsync thread name - {}", Thread.currentThread().getName());
        log.info("Task is: {}", task);
    }

    @Async("executorAsync")
    public void runWithExecutorAsync() {
        log.info("Currently Executing thread name - {}", Thread.currentThread().getName());
        log.info("User created with thread pool executor");
    }

    @Async
    public Future<String> asyncMethodWithReturnType() {
        log.info("Execute method asynchronously - "
                + Thread.currentThread().getName());
        try {
            Thread.sleep(5000);
            return new AsyncResult<>("hello world !!!!");
        } catch (InterruptedException e) {
            //
        }

        return null;
    }

    @Async
    public void asyncMethodWithVoidReturnType() {
        log.info("Execute method asynchronously. "
                + Thread.currentThread().getName());
    }

}
