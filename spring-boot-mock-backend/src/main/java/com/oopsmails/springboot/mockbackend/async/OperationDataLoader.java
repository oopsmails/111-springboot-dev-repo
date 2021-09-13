package com.oopsmails.springboot.mockbackend.async;

import java.util.Map;

public interface OperationDataLoader<I, R> {
    default void handleParams(I input, R output, Map<String, Object> operationTaskContextParamsMap) {
        if (operationTaskContextParamsMap == null || operationTaskContextParamsMap.isEmpty()) {
            return;
        }
    }
    void loadData(OperationTaskContext<I, R> operationTaskContext);
}
