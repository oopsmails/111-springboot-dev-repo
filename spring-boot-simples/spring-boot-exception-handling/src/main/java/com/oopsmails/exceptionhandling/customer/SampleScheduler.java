package com.oopsmails.exceptionhandling.customer;

import com.oopsmails.common.annotation.model.logging.LoggingOrigin;
import com.oopsmails.common.annotation.performance.LoggingPerformance;
import com.oopsmails.exceptionhandling.customer.service.CustomerService;
import com.oopsmails.exceptionhandling.mutex.service.IMutexService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@Slf4j
@ConditionalOnProperty("oopsmails.scheduler.sampleScheduler.enabled")
public class SampleScheduler {
    private final static String MUTEX_SCHEDULER_SAMPLE = "MUTEX_SCHEDULER_SAMPLE";

    @Autowired
    private CustomerService customerService;

    @Autowired
    private IMutexService mutexService;

    @Value("${oopsmails.scheduler.recovery.in.ms}")
    private long schedulerRecoveryInMillis;

    @LoggingPerformance(origin = LoggingOrigin.Service, transaction = "scheduledReporting")
    @Scheduled(fixedDelayString = "${oopsmails.scheduler.sampleScheduler.fix.delay.in.ms}",
            initialDelayString = "${oopsmails.scheduler.sampleScheduler.initial.delay.in.ms}")
    public void scheduledReporting() {
        log.info("Start scheduledReporting ...");

        try {
            if (!this.mutexService.lock(MUTEX_SCHEDULER_SAMPLE, Duration.ofMillis(schedulerRecoveryInMillis))) {
                log.info("mutex was already acquired by a different host, resourceCode: {}", MUTEX_SCHEDULER_SAMPLE);
                return;
            }
        } catch (Throwable throwable) {
            log.warn("mutex acquiring failed: {}", throwable.getMessage());
            throw throwable;
        }

        try {
            Thread.sleep(5000);
            this.customerService.generateForScheduler();
        } catch (Throwable throwable) {
            log.error("customerService.generateForScheduler failed: {}", throwable.getMessage());
            try {
                throw throwable;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } finally {
            this.mutexService.release(MUTEX_SCHEDULER_SAMPLE);
        }

        log.info("Completed scheduledReporting ...");
    }
}
