package com.oopsmails.springboot.mockbackend.async;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperationTaskContext<I, O> { // every task has its own Input Output
    private I taskInput;
    private O taskOutput;

    private Map<String, Object> operationTaskContextParamsMap;
}
