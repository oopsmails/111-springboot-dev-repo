package com.oopsmails.springboot.mockbackend.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.oopsmails.springboot.mockbackend.filter.model.GeneralLoggingRequest;
import com.oopsmails.springboot.mockbackend.filter.model.GeneralLoggingRequestWrapper;
import com.oopsmails.springboot.mockbackend.filter.model.GeneralLoggingResponse;
import com.oopsmails.springboot.mockbackend.filter.model.GeneralLoggingResponseWrapper;
import com.oopsmails.springboot.mockbackend.filter.model.HttpStatusUtils;
import com.oopsmails.springboot.mockbackend.utils.GeneralConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;


@Slf4j
@Component
@Order()
public class GeneralLoggingFilter implements Filter {

    private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    @Value("${general.logging.req.res}")
    private boolean isLoggingReqRes;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("GeneralLoggingFilter initialized.");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        String correlationId = getOrGenerateCorrelationId((HttpServletRequest) servletRequest);
        MDC.put(GeneralConstants.MDC_CORRELATION_ID, correlationId);

        GeneralLoggingRequestWrapper generalLoggingRequestWrapper = null;
        GeneralLoggingResponseWrapper generalLoggingResponseWrapper = null;

        boolean isHealthCheckEndpoint = false;
        try {
            isHealthCheckEndpoint = ((HttpServletRequest) servletRequest).getRequestURI().contains("health");

            try {
                generalLoggingRequestWrapper = new GeneralLoggingRequestWrapper((HttpServletRequest) servletRequest);
                generalLoggingResponseWrapper = new GeneralLoggingResponseWrapper((HttpServletResponse) servletResponse);
            } catch (Throwable throwable) {
                log.warn("Failed to create request response wrapper, {}", throwable.getMessage(), throwable);
            }

            if (isLoggingReqRes && !isHealthCheckEndpoint && generalLoggingRequestWrapper != null) {
                GeneralLoggingRequest generalLoggingRequest = new GeneralLoggingRequest(generalLoggingRequestWrapper);
                log.info("[Request]: {}", objectMapper.writeValueAsString(generalLoggingRequest));
            }

            if (generalLoggingRequestWrapper != null && generalLoggingResponseWrapper != null) {
                filterChain.doFilter(generalLoggingRequestWrapper, generalLoggingResponseWrapper);
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        } finally {
            if (generalLoggingResponseWrapper != null) {
                GeneralLoggingResponse generalLoggingResponse = new GeneralLoggingResponse(generalLoggingResponseWrapper);
                boolean hasHttpError = HttpStatusUtils.isError(generalLoggingResponse.getStatus());
                if (isHealthCheckEndpoint && hasHttpError) {
                    log.info("Server Health Check is down");
                }
                if (isLoggingReqRes && !isHealthCheckEndpoint || isHealthCheckEndpoint && hasHttpError) {
                    log.info("[Response]: {}", objectMapper.writeValueAsString(generalLoggingResponse));
                }
            }

            MDC.remove(GeneralConstants.MDC_CORRELATION_ID);
        }

        log.info("Exiting GeneralLoggingFilter");
    }

    @Override
    public void destroy() {
        log.info("Destroying GeneralLoggingFilter");
    }

    private String getOrGenerateCorrelationId(HttpServletRequest servletRequest) {
        String result = servletRequest.getHeader(GeneralConstants.REQUEST_HEADER_CORRELATION_ID);
        if (StringUtils.isBlank(result)) {
            result = UUID.randomUUID().toString();
        }

        return result;
    }
}
