package com.oopsmails.springboot.mockbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinWorkerThread;

@Configuration
@EnableAsync
public class AsyncConfig {
    private static final int THREAD_POOL_SIZE = 100;
    @Bean
    public Executor generalTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("generalTaskExecutor-");
        executor.initialize();
        return executor;
    }

    @Bean("fixedThreadPool")
    public ExecutorService executorService() { // used in OperationService
        return Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    }

    @Bean("appForkJoinPool")
    public ForkJoinPool appForkJoinPool() { // used in Github user
        final ForkJoinPool.ForkJoinWorkerThreadFactory factory = pool -> {
            final ForkJoinWorkerThread worker = ForkJoinPool.defaultForkJoinWorkerThreadFactory.newThread(pool);
            worker.setName("appForkJoinPool-" + worker.getPoolIndex());
            return worker;
        };

        return new ForkJoinPool(Runtime.getRuntime().availableProcessors(), factory, null, false);
    }
}
