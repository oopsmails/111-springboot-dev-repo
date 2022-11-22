package com.oopsmails.generaljava;

import com.oopsmails.common.tool.logging.RunningTimeLoggingService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;

import java.time.Duration;
import java.time.Instant;

@Slf4j
public class MethodExecutionTime {

    public static void main(String[] args) {
        MethodExecutionTime obj = new MethodExecutionTime();
        long start1 = System.nanoTime();
        obj.test();
        long end1 = System.nanoTime();
        log.info("Elapsed Time in nano seconds: {}", (end1 - start1));

        long start2 = System.currentTimeMillis();
        obj.test();
        long end2 = System.currentTimeMillis();
        log.info("Elapsed Time in milli seconds: {}", (end1 - start1));

        Instant inst1 = Instant.now();
        obj.test();
        Instant inst2 = Instant.now();
        log.info("Elapsed Time: {}", Duration.between(inst1, inst2).toString());

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        obj.test();
        stopWatch.stop();
        log.info("Elapsed Time in minutes: {}", stopWatch.getTime());

        RunningTimeLoggingService.logInfoExecutionTimeOfRunnable(() -> {
            obj.test();
        });

        String testNoArgWithReturnResult = RunningTimeLoggingService.logInfoExecutionTimeOfCallable(() -> {
            return obj.testNoArgWithReturn();
        });
        log.info("testNoArgWithReturnResult = {}", testNoArgWithReturnResult);

        String testNoArgWithReturnResultSupplier = RunningTimeLoggingService.supplierGetWithLogging(() -> {
            return obj.testNoArgWithReturn();
        });
        log.info("testNoArgWithReturnResultSupplier = {}", testNoArgWithReturnResultSupplier);

        String paramT = "str-paramT";
        String testFunctionResult = RunningTimeLoggingService.functionApplyWithLogging(
                paramT,
                (t) -> {
                    return obj.testFunction(t);
                }
        );
        log.info("testFunctionResult = {}", testFunctionResult);

        String paramU = "str-paramU";
        String testBiFunctionResult = RunningTimeLoggingService.logInfoExecutionTimeOfBiFunction(
                paramT,
                paramU,
                (t, u) -> {
                    return obj.testBiFunction(t, u);
                }
        );
        log.info("testBiFunctionResult = {}", testBiFunctionResult);
    }

    @SneakyThrows
    public void test() {
        int num = 0;
        Thread.sleep(1000);
        for (int i = 0; i <= 50; i++) {
            num = num + i;
        }
    }

    @SneakyThrows
    public String testNoArgWithReturn() {
        Thread.sleep(1000);
        return "Hello from method testNoArgWithReturn()";
    }

    @SneakyThrows
    public String testFunction(String paramT) {
        Thread.sleep(1000);
        return "Hello from method testFunction(paramT), paramT = " + paramT;
    }

    @SneakyThrows
    public String testBiFunction(String paramT, String paramU) {
        Thread.sleep(1000);
        return "Hello from method testBiFunction(paramT), paramT = " + paramT + ", paramU = " + paramU;
    }
}
