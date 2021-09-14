package com.oopsmails.springboot.mockbackend.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public abstract class AbstractOperationDataLoader implements OperationDataLoader {
    @Autowired
    protected OperationDataLoaderManager operationDataLoaderManager;

    @Override
    public abstract void loadData(OperationTaskContext operationTaskContext);
}
