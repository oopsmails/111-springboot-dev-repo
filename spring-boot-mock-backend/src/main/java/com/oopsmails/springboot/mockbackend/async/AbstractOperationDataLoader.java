package com.oopsmails.springboot.mockbackend.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public abstract class AbstractOperationDataLoader<I, O> implements OperationDataLoader<I, O> {
    @Autowired
    protected OperationDataLoaderManager operationDataLoaderManager;

    @Override
    public void loadData(OperationTaskContext<I, O> operationTaskContext) {
        if (operationTaskContext == null) {
            log.warn("Passed in operationTaskContext is null");
            return;
        }
        reviseInput(operationTaskContext.getTaskInput(), operationTaskContext.getOperationTaskContextParamsMap());
        loadData(operationTaskContext.getTaskInput(), operationTaskContext.getTaskOutput());
        reviseOutput(operationTaskContext.getTaskOutput(), operationTaskContext.getOperationTaskContextParamsMap());
    }

    @Override
    public void reviseInput(I input, Map<String, Object> operationTaskContextParamsMap) {
        // revise input based on paramsMap if necessary
    }

    @Override
    public void reviseOutput(O output, Map<String, Object> operationTaskContextParamsMap) {
        // revise output based on paramsMap if necessary
    }

    protected abstract void loadData(I input, O output);
}
