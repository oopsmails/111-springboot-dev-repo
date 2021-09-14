package com.oopsmails.springboot.mockbackend.async;

import org.springframework.beans.factory.annotation.Autowired;

public class TestDataLoader implements OperationDataLoader {
    @Autowired
    private OperationDataLoaderManager operationDataLoaderManager;

    @Override
    public void loadData(OperationTaskContext operationTaskContext) {
        // for testing
    }
}
