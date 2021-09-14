package com.oopsmails.springboot.mockbackend.async;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestDataLoader implements OperationDataLoader {
    @Autowired
    private OperationDataLoaderManager operationDataLoaderManager;

    @Override
    public void loadData(OperationTaskContext operationTaskContext) {
        MockDelayObject mockDelayObject = operationDataLoaderManager.getMockDelayObjectTestLoader((String) operationTaskContext.getTaskInput());
        operationTaskContext.setTaskOutput(mockDelayObject);
    }
}
