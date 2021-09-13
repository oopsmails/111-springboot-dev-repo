package com.oopsmails.springboot.mockbackend.async;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperationTaskContext<I, R> {
    private I taskInput;
    private R taskOutput;

    private Map<String, Object> operationTaskContextParamsMap;
}
