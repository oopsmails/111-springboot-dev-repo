package com.oopsmails.springboot.mockbackend.async;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class OperationContext {
    private List<OperationTask> operationTasks = new ArrayList<>();
    private Map<String, Object> operationContextParamsMap;
    private boolean runInParallel;

    public void addOperationTask(OperationTask operationTask) {
        getOperationTasks().add(operationTask);
    }

    public void clearOperationTasks() {
        getOperationTasks().clear();
    }
}
