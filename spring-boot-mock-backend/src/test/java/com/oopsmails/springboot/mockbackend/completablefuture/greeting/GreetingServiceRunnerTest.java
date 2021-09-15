package com.oopsmails.springboot.mockbackend.completablefuture.greeting;

import com.oopsmails.springboot.mockbackend.SpringBootBackendMockApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {SpringBootBackendMockApplication.class})
class GreetingServiceRunnerTest {

    @Autowired
    private GreetingServiceRunner greetingServiceRunner;

    @Test
    void testGetAll() throws Exception {
        List<String> langList = Arrays.asList("EN", "ES", "SN");
        List<String> result = greetingServiceRunner.runnerGetAllGreetings(langList, true);
        assertNotNull(result, "result should not be null.");
    }

    @Test
    void testGetAll_ExceptionHandling_Async() throws Exception {
        List<String> langList = Arrays.asList("EN", "ES", "SN", "EX");
        List<String> result = greetingServiceRunner.runnerGetAllGreetings(langList, true);
        assertNotNull(result, "result should not be null.");
    }

    @Test
    void testGetAll_ExceptionHandling_Sync() throws Exception {
        List<String> langList = Arrays.asList("EN", "ES", "SN", "EX");
        List<String> result = greetingServiceRunner.runnerGetAllGreetings(langList, false);
        assertNotNull(result, "result should not be null.");
    }
}
