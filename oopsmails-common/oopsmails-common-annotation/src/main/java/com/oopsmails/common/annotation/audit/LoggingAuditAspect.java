package com.oopsmails.common.annotation.audit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import com.oopsmails.common.annotation.model.logging.LoggingDtoAudit;
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

@Slf4j
@Aspect
@Component
public class LoggingAuditAspect {
    private final static ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Around("@annotation(com.oopsmails.common.annotation.audit.LoggingAudit)")
    public Object advice(ProceedingJoinPoint joinPoint) throws Throwable {
        boolean success = false;

        try {
            Object obj = joinPoint.proceed();
            success = true;
            return obj;
        } finally {
            if (joinPoint.getSignature() instanceof MethodSignature) {
                MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
                Method method = methodSignature.getMethod();
                LoggingAudit loggingAudit = method.getAnnotation(LoggingAudit.class);

                LoggingDtoAudit loggingDtoAudit = new LoggingDtoAudit();

                loggingDtoAudit.setMessage(loggingAudit.message());

                String correlationId = MDC.get(CommonLoggingConstants.MDC_CORRELATION_ID);
                loggingDtoAudit.setCorrelationId(StringUtils.isEmpty(correlationId) ? "" : correlationId);

                loggingDtoAudit.setTarget(getTarget(joinPoint));

                try {
                    LoggerFactory.getLogger(loggingDtoAudit.getLevel().getAppender()).info(objectMapper.writeValueAsString(loggingDtoAudit));
                } catch (JsonProcessingException e) {
                    log.error("Failed converting loggingDtoAudit to json: [{}]", loggingDtoAudit);
                }
            }
        }
    }

    private String getTarget(ProceedingJoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0) {
            return null;
        }

        if (args[0] instanceof AuditArg) {
            AuditArg auditArg = (AuditArg) args[0];
            return auditArg.getAuditArgTarget();
        }

        return null;
    }
}
