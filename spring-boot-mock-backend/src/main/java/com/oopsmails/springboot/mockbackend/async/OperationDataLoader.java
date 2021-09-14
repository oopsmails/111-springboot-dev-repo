package com.oopsmails.springboot.mockbackend.async;

public interface OperationDataLoader {
    void loadData(OperationTaskContext<?, ?> operationTaskContext);
}
