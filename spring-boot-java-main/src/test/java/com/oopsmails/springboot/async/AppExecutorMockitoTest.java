package com.oopsmails.springboot.async;

import com.oopsmails.springboot.javamain.TestFizzBuzz;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class AppExecutorMockitoTest {

    @InjectMocks
    private AppExecutor appExecutor;

    private List<String> taskList = new ArrayList<>();

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        for(int i = 0; i < 100; i++) {
            taskList.add("This is: " + i);
        }
    }

    @Test
    public void testAsync1() {
        String result = "abc";
        for(String task : taskList) {
            appExecutor.runWithExecutorAsync(task);
        }
        assertEquals("abc", result, "Should be eaqual!");
    }

    @Test
    public void testAsync2() {
        String result = "abc";
        appExecutor.runWithConcurrentExecutor(taskList);
        assertEquals("abc", result, "Should be eaqual!");
    }
}
