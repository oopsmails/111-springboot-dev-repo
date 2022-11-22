package com.oopsmails.generaljava;

import com.oopsmails.common.tool.logging.RunningTimeLoggingService;
import lombok.SneakyThrows;
import org.apache.commons.lang3.time.StopWatch;

import java.time.Duration;
import java.time.Instant;

public class MethodExecutionTime {

    public static void main(String[] args) {
        MethodExecutionTime obj = new MethodExecutionTime();
        long start1 = System.nanoTime();
        obj.test();
        long end1 = System.nanoTime();
        System.out.println("Elapsed Time in nano seconds: " + (end1 - start1));

        long start2 = System.currentTimeMillis();
        obj.test();
        long end2 = System.currentTimeMillis();
        System.out.println("Elapsed Time in milli seconds: " + (end2 - start2));

        Instant inst1 = Instant.now();
        obj.test();
        Instant inst2 = Instant.now();
        System.out.println("Elapsed Time: " + Duration.between(inst1, inst2).toString());

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        obj.test();
        stopWatch.stop();
        System.out.println("Elapsed Time in minutes: " + stopWatch.getTime());

        RunningTimeLoggingService.logInfoExecutionTimeOfRunnable(() -> {
            obj.test();
        });
    }

    @SneakyThrows
    public void test() {
        int num = 0;
        Thread.sleep(2000);
        for (int i = 0; i <= 50; i++) {
            num = num + i;
//            System.out.print(num + ", ");
        }
    }
}
