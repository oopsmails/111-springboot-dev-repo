package com.oopsmails.springboot.async;

import com.oopsmails.springboot.javamain.SpringBootJavaGenericTestBase;
import com.oopsmails.springboot.javamain.example.SpringBootExampleTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@SpringBootTest(classes = { //
//        AppExecutorSpringTest.AppExecutorSpringTestConfig.class, //
//})
@Slf4j
public class AppExecutorSpringTest extends SpringBootJavaGenericTestBase {
    @Autowired
    private Clock appClock;

    @Autowired
    private AppExecutor appExecutor;

    @Autowired
//    @Qualifier("forkJoinPool") // not needed
    private ForkJoinPool forkJoinPool;

    private List<String> taskList = new ArrayList<>();

    @BeforeEach
    public void setup() {
        for(int i = 0; i < 100; i++) {
            taskList.add("This is: " + i);
        }
    }

    @Test
    public void testBeanOverriding() throws Exception {
        LocalDate localDate = LocalDate.now(appClock);
        log.info("localDate = " + localDate);
    }

    @Test
    public void testRunWithExecutorAsync() {
        log.info("testRunWithExecutorAsync .....................");
        String result = "abc";
        for(String task : taskList) {
            appExecutor.runWithExecutorAsync(task);
        }
        assertEquals("abc", result, "Should be eaqual!");
    }

    @Test
    public void testRunWithConcurrentExecutor() {
        log.info("testRunWithConcurrentExecutor .....................");
        String result = "abc";
        appExecutor.runWithConcurrentExecutor(taskList);
        assertEquals("abc", result, "Should be eaqual!");
    }

    @Test
    public void testForkJoinPool() throws Exception {
        log.info("testForkJoinPool .....................");
        String result = "abc";

        String outsideString = "outsideString";
        List<String> resultList = forkJoinPool.submit(() ->
                taskList.parallelStream()
                        .map(task -> {
                            log.info("processing: {}", task);
                            return outsideString + ", " + task;
                        })
                        .filter(str -> str != null)
                        .collect(Collectors.toList())
        ).get();

        resultList.forEach(str -> log.info(str));
        assertEquals("abc", result, "Should be eaqual!");
    }

//    @TestConfiguration
//    @ComponentScan("com.oopsmails.springboot.async")
//    public static class AppExecutorSpringTestConfig {
//
//    }
}
