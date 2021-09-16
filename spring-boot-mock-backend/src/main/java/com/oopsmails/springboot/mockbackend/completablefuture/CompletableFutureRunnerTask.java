package com.oopsmails.springboot.mockbackend.completablefuture;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class CompletableFutureRunnerTask<I, O> { // TODO: list of this CompletableFutureRunnerTask can be run in parallel
    private I taskInput;
    private O taskOutput;

    private Map<String, Object> operationTaskContextParamsMap;
}
