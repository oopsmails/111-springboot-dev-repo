package com.oopsmails.springboot.mockbackend.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MockDelayServiceRestClient {
    public MockDelayObject getMockDelayObject(String strField) {
        try {
            log.info("getMockDelayObject: retrieving {}", strField);
            MockDelayObject mockDelayObject = new MockDelayObject();
            mockDelayObject.setMockStringField(strField);
            Thread.sleep(1000L);

            return mockDelayObject;
        } catch (InterruptedException e) {
            log.warn("Get InterruptedException: {}", e);
            // Restore interrupted state...
            Thread.currentThread().interrupt();
        }
        return null;
    }
}
