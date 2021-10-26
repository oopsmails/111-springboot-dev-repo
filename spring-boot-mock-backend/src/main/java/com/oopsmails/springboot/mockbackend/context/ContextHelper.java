package com.oopsmails.springboot.mockbackend.context;

import com.oopsmails.springboot.mockbackend.annotation.audit.LoggingAudit;
import com.oopsmails.springboot.mockbackend.model.logging.LoggingOrigin;
import com.oopsmails.springboot.mockbackend.utils.GeneralConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;

@Component
@Slf4j
public class ContextHelper implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static <R, T> R runWithAuditing(T data, String message, Callable<R> callable) throws Exception {
        ContextHelper bean = getBean();
        if (bean == null) {
            log.info("run runWithAuditing, bean is null, message: {}", message);
            log.info("runWithAuditLog, run with correlationId = {}", MDC.get(GeneralConstants.MDC_CORRELATION_ID));
            return callable.call();
        } else {
            log.info("runWithAuditLog, run with correlationId = {}", MDC.get(GeneralConstants.MDC_CORRELATION_ID));
            return bean.runWithAuditLog(data, message, callable);
        }
    }

    @LoggingAudit(origin = LoggingOrigin.Runners, message = "running with Audit loggign ... ")
    public <R, T> R runWithAuditLog(
            @LoggingAudit(origin = LoggingOrigin.Runners, type = "input") T data,
            @LoggingAudit(origin = LoggingOrigin.Runners, type = "message") String message,
            Callable<R> callable) throws Exception {
        log.info("run runWithAuditLog, message: {}", message);
        log.info("runWithAuditLog, run with correlationId = {}", MDC.get(GeneralConstants.MDC_CORRELATION_ID));
        return callable.call();
    }

    @LoggingAudit(origin = LoggingOrigin.Runners, message = "running with Audit logging ... ")
    public <R, T> R runWithAuditLog(
            @LoggingAudit(origin = LoggingOrigin.Runners, type = "input") T data,
            @LoggingAudit(origin = LoggingOrigin.Runners, type = "message") String message,
            Function<T, R> function) throws Exception {
        log.info("run runWithAuditLog, message: {}", message);
        log.info("runWithAuditLog, run with correlationId = {}", MDC.get(GeneralConstants.MDC_CORRELATION_ID));
        return function.apply(data);
    }

    public <T> void runWithCorrelationId(T data, Consumer<T> consumer) {
        try {
            initCorrelationId();
            log.info("runWithCorrelationId, run with correlationId = {}", MDC.get(GeneralConstants.MDC_CORRELATION_ID));
            consumer.accept(data);
        } finally {
            log.info("runWithCorrelationId, removing correlationId = {}", MDC.get(GeneralConstants.MDC_CORRELATION_ID));
            // ContextHolder.setCorrelationId(null); // will implement ContextHolder later.
            MDC.put(GeneralConstants.MDC_CORRELATION_ID, null);
        }
    }

    private void initCorrelationId() {
        String correlationId = MDC.get(GeneralConstants.MDC_CORRELATION_ID);
        if (StringUtils.isEmpty(correlationId)) {
            UUID uuid = UUID.randomUUID();
            correlationId = uuid.toString();
            MDC.put(GeneralConstants.MDC_CORRELATION_ID, correlationId);
        }
        // ContextHolder.setCorrelationId(correlationId); // will implement ContextHolder later.
    }

    private static ContextHelper getBean() {
        if (context == null) {
            return  null;
        }

        return context.getBean(ContextHelper.class);
    }
}
