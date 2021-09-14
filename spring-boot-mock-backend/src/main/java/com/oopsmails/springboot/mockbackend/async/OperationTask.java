package com.oopsmails.springboot.mockbackend.async;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OperationTask<I, O> {
    private OperationTaskContext<I, O> operationTaskContext;
    private OperationDataLoader operationDataLoader;
}
