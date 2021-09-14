package com.oopsmails.springboot.mockbackend.async;

import java.util.Map;

public interface OperationDataLoader<I, O> {
    void reviseInput(I input, Map<String, Object> operationTaskContextParamsMap);
    void reviseOutput(O output, Map<String, Object> operationTaskContextParamsMap);
    void loadData(OperationTaskContext<I, O> operationTaskContext);
}
