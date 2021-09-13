package com.oopsmails.springboot.mockbackend.async;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class MockDelayOperationDataLoader extends AbstractOperationDataLoader<String, MockDelayOperationDataLoaderOutput> {

    @Override
    protected void loadData(String input, MockDelayOperationDataLoaderOutput output) {
        MockDelayObject mockDelayObject = operationDataLoaderManager.getMockDelayObject(input);
        output.setMockDelayObject(mockDelayObject);
    }

    @Override
    public void handleParams(String input, MockDelayOperationDataLoaderOutput output, Map<String, Object> operationTaskContextParamsMap) {
        super.handleParams(input, output, operationTaskContextParamsMap);
//        output.setPassingAroundParam((String) operationTaskContextParamsMap.get(MockDelayService.TEST_OPERATION_TASK_CONTEXT_PARAM));
    }
}
