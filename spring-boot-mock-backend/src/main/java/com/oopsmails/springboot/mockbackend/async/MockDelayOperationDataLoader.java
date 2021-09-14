package com.oopsmails.springboot.mockbackend.async;

import org.springframework.stereotype.Component;

@Component
public class MockDelayOperationDataLoader extends AbstractOperationDataLoader {
    @Override
    public void loadData(OperationTaskContext operationTaskContext) {
        MockDelayObject mockDelayObject = operationDataLoaderManager.getMockDelayObject((String) operationTaskContext.getTaskInput());
        ((MockDelayOperationDataLoaderOutput) operationTaskContext.getTaskOutput()).setMockDelayObject(mockDelayObject);
    }
}
