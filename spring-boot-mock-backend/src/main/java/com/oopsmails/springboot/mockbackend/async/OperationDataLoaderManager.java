package com.oopsmails.springboot.mockbackend.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class OperationDataLoaderManager {

    private RestTemplate restTemplate;

    @Autowired
    private MockDelayServiceRestClient mockDelayServiceRestClient;

    public MockDelayObject getMockDelayObject(String strField) {
        return mockDelayServiceRestClient.getMockDelayObject(strField);
    }
}
