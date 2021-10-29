package com.oopsmails.common.annotation.performance;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.oopsmails.common.annotation.model.logging.LoggingDtoPerformance;
import com.oopsmails.common.constant.CommonLoggingConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.Duration;
import java.time.Instant;

@Slf4j
@Aspect
@Component
public class LoggingPerformanceAspect {
    private final static ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Around("@annotation(com.oopsmails.common.annotation.performance.LoggingPerformance)")
    public Object advice(ProceedingJoinPoint joinPoint) throws Throwable {
        if (!(joinPoint.getSignature() instanceof MethodSignature)) { // only log for methods
            return joinPoint.proceed();
        }

        Instant instantStart = Instant.now();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        LoggingPerformance loggingAnnotation = method.getAnnotation(LoggingPerformance.class);

        try {
            return joinPoint.proceed();
        } finally {
            Instant instantEnd = Instant.now();
            long duration = Duration.between(instantStart, instantEnd).toMillis();

            LoggingDtoPerformance loggingDtoPerformance = new LoggingDtoPerformance();

            loggingDtoPerformance.setDuration(duration);
            loggingDtoPerformance.setThread(Thread.currentThread().getName());
            loggingDtoPerformance.setSignature(joinPoint.getSignature().toString());
            loggingDtoPerformance.setMessage(loggingAnnotation.message());
            loggingDtoPerformance.setOrigin(loggingAnnotation.origin());
            loggingDtoPerformance.setStatus("");

            String correlationId = MDC.get(CommonLoggingConstants.MDC_CORRELATION_ID);
            loggingDtoPerformance.setCorrelationId(StringUtils.isEmpty(correlationId) ? "" : correlationId);

            try {
                LoggerFactory.getLogger(loggingDtoPerformance.getLevel().getAppender()).info(objectMapper.writeValueAsString(loggingDtoPerformance));
            } catch (JsonProcessingException e) {
                log.error("Failed converting loggingDtoPerformance to json: [{}]", loggingDtoPerformance);
            }
        }
    }
}
