package com.oopsmails.springboot.mockbackend.async;

import com.oopsmails.springboot.mockbackend.SpringBootBackendMockApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {SpringBootBackendMockApplication.class})
public class MockDelayServiceTest {
    @Autowired
    private MockDelayService mockDelayService;

    @Test
    public void testFindAll() throws Exception {
        List<MockDelayObject> result = mockDelayService.findAllMockDelayObjects();

        assertNotNull(result, "result should not be null.");
    }

    @Test
    public void testFindAllIfParallel() throws Exception {
        List<MockDelayObject> result = mockDelayService.findAllMockDelayObjectsAsync();

        assertNotNull(result, "result should not be null.");
    }
}
