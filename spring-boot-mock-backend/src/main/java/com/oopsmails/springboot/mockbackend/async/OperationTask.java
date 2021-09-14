package com.oopsmails.springboot.mockbackend.async;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
//@AllArgsConstructor
public class OperationTask<I, O> {
    private OperationTaskContext operationTaskContext;
    private OperationDataLoader<I, O> operationDataLoader;
}
