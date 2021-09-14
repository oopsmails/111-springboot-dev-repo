package com.oopsmails.springboot.mockbackend.async;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MockDelayOperationDataLoader extends AbstractOperationDataLoader<String, MockDelayOperationDataLoaderOutput> {

    @Override
    protected void loadData(String input, MockDelayOperationDataLoaderOutput output) {
        MockDelayObject mockDelayObject = operationDataLoaderManager.getMockDelayObject(input);
        output.setMockDelayObject(mockDelayObject);
    }

    @Override
    public void reviseOutput(MockDelayOperationDataLoaderOutput output, Map<String, Object> operationTaskContextParamsMap) {
        output.setPassingAroundParam((String) operationTaskContextParamsMap.get(MockDelayService.TEST_OPERATION_CONTEXT_PARAM));
    }
}
