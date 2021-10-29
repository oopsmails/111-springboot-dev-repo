package com.oopsmails.springboot.mockbackend.async;

import com.oopsmails.common.annotation.model.logging.LoggingOrigin;
import com.oopsmails.common.annotation.performance.LoggingPerformance;
import com.oopsmails.springboot.mockbackend.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class MockDelayService {
    public static final String TEST_OPERATION_CONTEXT_PARAM = "TEST_OPERATION_CONTEXT_PARAM";
    public static final String TEST_OPERATION_TASK_CONTEXT_PARAM = "TEST_OPERATION_TASK_CONTEXT_PARAM";

    @Autowired
    private MockDelayServiceRestClient mockDelayServiceRestClient;

    @Autowired
    private OperationService<OperationContext> operationService;

    public List<MockDelayObject> findAllMockDelayObjects() {
        List<MockDelayObject> result = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            MockDelayObject mockDelayObject = mockDelayServiceRestClient.getMockDelayObject("" + i);
            result.add(mockDelayObject);
        }
        JsonUtils.printJsonObject(result);
        return result;
    }

    @LoggingPerformance(origin = LoggingOrigin.Service, message = "MockDelayService message ... ")
    public List<MockDelayObject> findAllMockDelayObjectsAsync() {
        MockDelayServiceOperationContext operationContext = new MockDelayServiceOperationContext();

        final Map<String, Object> operationContextParamsMap = new HashMap<>();
        operationContextParamsMap.put(TEST_OPERATION_CONTEXT_PARAM, "TEST_OPERATION_CONTEXT_PARAM in MockDelayService");
        operationContext.setOperationContextParamsMap(operationContextParamsMap);

        final Map<String, Object> operationTaskContextParamsMap = new HashMap<>();
        operationTaskContextParamsMap.put(TEST_OPERATION_TASK_CONTEXT_PARAM, "TEST_OPERATION_TASK_CONTEXT_PARAM in MockDelayService");

        List<String> byIds = new ArrayList<>();

        for(int i = 0; i < 5; i++) {
            byIds.add("" + i);
        }

        operationContext.setByIds(byIds);
        List<MockDelayObject> result = operationService.findMockDelayObjectsByIds(operationContext);
        JsonUtils.printJsonObject(result);
        return result;
    }
}
