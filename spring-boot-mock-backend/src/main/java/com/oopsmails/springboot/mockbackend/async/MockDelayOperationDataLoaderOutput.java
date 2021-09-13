package com.oopsmails.springboot.mockbackend.async;

import lombok.Data;

@Data
public class MockDelayOperationDataLoaderOutput {
    private MockDelayObject mockDelayObject;
    private String passingAroundParam;
}
