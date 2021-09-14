package com.oopsmails.springboot.mockbackend.async;

public interface OperationDataLoader<I, O> {
    void loadData(OperationTaskContext<I, O> operationTaskContext);
}
