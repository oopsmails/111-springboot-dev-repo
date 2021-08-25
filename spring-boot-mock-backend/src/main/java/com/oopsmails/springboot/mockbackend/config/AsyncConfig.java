package com.oopsmails.springboot.mockbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinWorkerThread;

@EnableAsync
public class AsyncConfig {
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

    @Bean("appForkJoinPool")
    public ForkJoinPool appForkJoinPool() {
        final ForkJoinPool.ForkJoinWorkerThreadFactory factory = new ForkJoinPool.ForkJoinWorkerThreadFactory()
        {
            @Override
            public ForkJoinWorkerThread newThread(ForkJoinPool pool)
            {
                final ForkJoinWorkerThread worker = ForkJoinPool.defaultForkJoinWorkerThreadFactory.newThread(pool);
                worker.setName("appForkJoinPool-" + worker.getPoolIndex());
                return worker;
            }
        };

        ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors(), factory, null, false);

        return forkJoinPool;
    }
}
