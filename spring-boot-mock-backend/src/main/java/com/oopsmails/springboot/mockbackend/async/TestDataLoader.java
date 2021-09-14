package com.oopsmails.springboot.mockbackend.async;

import org.springframework.beans.factory.annotation.Autowired;

public class TestDataLoader implements OperationDataLoader<String, MockDelayObject> {
    @Autowired
    private OperationDataLoaderManager operationDataLoaderManager;

    @Override
    public void loadData(OperationTaskContext<String, MockDelayObject> operationTaskContext) {
        MockDelayObject mockDelayObject = operationDataLoaderManager.getMockDelayObject(operationTaskContext.getTaskInput());
        operationTaskContext.setTaskOutput(mockDelayObject);
    }
}
