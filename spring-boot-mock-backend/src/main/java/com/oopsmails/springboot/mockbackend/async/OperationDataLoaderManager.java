package com.oopsmails.springboot.mockbackend.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OperationDataLoaderManager {

    @Autowired
    private MockDelayServiceRestClient mockDelayServiceRestClient;

    public MockDelayObject getMockDelayObject(String strField) {
        return mockDelayServiceRestClient.getMockDelayObject(strField);
    }

    public MockDelayObject getMockDelayObjectTestLoader(String strField) {
        return mockDelayServiceRestClient.getMockDelayObject(strField);
    }
}
