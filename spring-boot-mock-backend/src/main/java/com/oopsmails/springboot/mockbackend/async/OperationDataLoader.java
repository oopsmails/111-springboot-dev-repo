package com.oopsmails.springboot.mockbackend.async;

import java.util.Map;

public interface OperationDataLoader<I, O> { // "I" is used to define Loader Input, "O" is used to define Loader Output
    void reviseInput(I input, Map<String, Object> operationTaskContextParamsMap);
    void reviseOutput(O output, Map<String, Object> operationTaskContextParamsMap);
    void loadData(OperationTaskContext<I, O> operationTaskContext);
}
