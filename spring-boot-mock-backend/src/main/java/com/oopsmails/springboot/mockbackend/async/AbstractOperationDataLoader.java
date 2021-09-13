package com.oopsmails.springboot.mockbackend.async;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public abstract class AbstractOperationDataLoader<I, R> implements OperationDataLoader<I, R> {
    @Autowired
    protected OperationDataLoaderManager operationDataLoaderManager;

    @Override
    public void loadData(OperationTaskContext<I, R> operationTaskContext) {
        loadData(operationTaskContext.getTaskInput(), operationTaskContext.getTaskOutput(), operationTaskContext.getOperationTaskContextParamsMap());
    }

    protected abstract void loadData(I input, R output);

    // the logic in operationTaskContextParamsMap should be handled in subclass, if any.
    protected void loadData(I input, R output, Map<String, Object> operationTaskContextParamsMap) {
        handleParams(input, output, operationTaskContextParamsMap);
        loadData(input, output);
    }
}
