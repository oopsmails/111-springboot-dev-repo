package com.oopsmails.springboot.async;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executors;

@Configuration
//@EnableAsync
public class SpringAsyncConfig {
    // example for application wide executor service
   /* @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(4);
        taskExecutor.setMaxPoolSize(4);
        taskExecutor.setQueueCapacity(50);
        taskExecutor.initialize();
        return taskExecutor;
    } */

    /**/
    @Bean(name = "executorAsync")
    public TaskExecutor executorAsync() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(4);
        executor.setThreadNamePrefix("executorAsync_thread");
        executor.initialize();
        return executor;
    }

    @Bean(name = "executorScheduled")
    public TaskExecutor executorScheduled() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(4);
        executor.setThreadNamePrefix("executorScheduled_thread");
        executor.initialize();
        return executor;
    }

    @Bean(name = "concurrentTaskExcutor")
    public TaskExecutor concurrentTaskExcutor() {
        return new ConcurrentTaskExecutor(Executors.newFixedThreadPool(5));
    }


}
