package com.oopsmails.springboot.mockbackend.async;

import com.oopsmails.springboot.mockbackend.model.general.OopsTimeout;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class OperationService<T extends OperationContext> {
    @Value("${thread.pool.size}")
    private int threadPoolSize;

    @Value("${data.loader.timeout.in.second}")
    private int dataLoaderTimeout;

    @Value("#{'${data.loader.in.parallel}'.split(',')}")
    private Set<String> operationContextDataLoadingInParallel;

    @Autowired
    @Qualifier("fixedThreadPool") // if no Qualifier, then will use appForkJoinPool
    private ExecutorService executorService;

    @Autowired
    private MockDelayOperationDataLoader mockDelayOperationDataLoader;

    @Autowired
    private TestDataLoader testDataLoader;

    public List<MockDelayObject> findMockDelayObjectsByIds(T operationContext) {
        List<MockDelayObject> result = new ArrayList<>();
        if (operationContext instanceof MockDelayServiceOperationContext) {
            MockDelayServiceOperationContext mockDelayServiceOperationContext = (MockDelayServiceOperationContext) operationContext;
            List<String> byIds = mockDelayServiceOperationContext.getByIds();
            if (byIds == null || byIds.isEmpty()) {
                return result;
            }
            for (String id : byIds) {
                OperationTaskContext<String, MockDelayOperationDataLoaderOutput> operationTaskContext = new OperationTaskContext<String, MockDelayOperationDataLoaderOutput>() {
                    @Override
                    public Object getRealTaskOutput() {
                        return getTaskOutput().getMockDelayObject();
                    }
                };
                operationTaskContext.setTaskInput(id);
                MockDelayOperationDataLoaderOutput mockDelayOperationDataLoaderOutput = new MockDelayOperationDataLoaderOutput();
                operationTaskContext.setTaskOutput(mockDelayOperationDataLoaderOutput);
                operationTaskContext.setOperationTaskContextParamsMap(operationContext.getOperationContextParamsMap());

                OperationTask<String, MockDelayOperationDataLoaderOutput> operationTask = new OperationTask<>();
                operationTask.setOperationTaskContext(operationTaskContext);
                operationTask.setOperationDataLoader(this.mockDelayOperationDataLoader);
                mockDelayServiceOperationContext.addOperationTask(operationTask);
            }

            OperationTaskContext<String, MockDelayObject> operationTaskContext = new OperationTaskContext<String, MockDelayObject>() {
                @Override
                public Object getRealTaskOutput() {
                    return getTaskOutput();
                }
            };
            operationTaskContext.setTaskInput("extraTestingId");
            operationTaskContext.setOperationTaskContextParamsMap(operationContext.getOperationContextParamsMap());

            OperationTask<String, MockDelayObject> operationTaskTest = new OperationTask<>();
            operationTaskTest.setOperationTaskContext(operationTaskContext);
            operationTaskTest.setOperationDataLoader(this.testDataLoader);
            mockDelayServiceOperationContext.addOperationTask(operationTaskTest);

            performOperation(operationContext);

            mockDelayServiceOperationContext.getOperationTasks().forEach(operationTaskIn -> {
//                OperationTask<String, MockDelayOperationDataLoaderOutput> operationTask = (OperationTask<String, MockDelayOperationDataLoaderOutput>) operationTaskIn;
//                MockDelayOperationDataLoaderOutput output = operationTask.getOperationTaskContext().getTaskOutput();
//                output.setPassingAroundParam("" + operationContext.getOperationContextParamsMap().get(MockDelayService.TEST_OPERATION_CONTEXT_PARAM));
//                log.info("output.getPassingAroundParam() = [{}]", output.getPassingAroundParam());
//                result.add(output.getMockDelayObject());

                MockDelayObject output = (MockDelayObject) ((OperationTask)operationTaskIn).getOperationTaskContext().getRealTaskOutput();
                result.add(output);
            });
        } else {
            log.warn("findMockDelayObjectsByIds: operationContext is not MockDelayServiceOperationContext");
        }
        return result;
    }

    public void performOperation(T operationContext) {
        if (operationContext == null || operationContext.getOperationTasks() == null || operationContext.getOperationTasks().isEmpty()) {
            log.warn("No task to execute ... ");
            return;
        }
        String operationContextClass = operationContext.getClass().getSimpleName();
        boolean inParallel = operationContextDataLoadingInParallel.contains(operationContextClass);
        OopsTimeout oopsTimeout = new OopsTimeout(this.dataLoaderTimeout, TimeUnit.SECONDS);
        log.info("Start loading: parallel? {}", inParallel);

        // all results are in individual taskOutput of each OperationTask
        OperationTaskUtil.executeOperation(operationContext, this.executorService, oopsTimeout, inParallel);
    }
}
