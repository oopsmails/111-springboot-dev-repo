package com.oopsmails.common.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.oopsmails.common.filter.domain.CommonLoggingRequest;
import com.oopsmails.common.filter.domain.CommonLoggingRequestWrapper;
import com.oopsmails.common.filter.domain.CommonLoggingResponse;
import com.oopsmails.common.filter.domain.CommonLoggingResponseWrapper;
import com.oopsmails.common.filter.util.FilterUtil;
import com.oopsmails.common.constant.CommonLoggingConstants;
import com.oopsmails.common.filter.util.HttpStatusUtil;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Set;


@Slf4j
@Component
@Order()
public class CommonLoggingFilter implements Filter {

    private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Value("${general.logging.req.res: true}")
    private boolean isLoggingReqRes;

    @Value("#{'${logging.exemption.urls}'.split(',')}")
    private Set<String> loggingExemptionUrls;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("CommonLoggingFilter initialized.");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        String path = httpServletRequest.getRequestURI();
        log.info("doFilter(), req.getRequestURI(): {}", path);

        if (!isLoggingReqRes || FilterUtil.isExemptionUrl(path, loggingExemptionUrls)) {
            log.warn("Skipping logging, isLoggingReqRes = {} or {} is inExemption", isLoggingReqRes, path);
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        CommonLoggingRequestWrapper commonLoggingRequestWrapper = null;
        CommonLoggingResponseWrapper commonLoggingResponseWrapper = null;

        boolean isHealthCheckEndpoint = false;
        try {
            isHealthCheckEndpoint = ((HttpServletRequest) servletRequest).getRequestURI().contains("health");

            try {
                commonLoggingRequestWrapper = new CommonLoggingRequestWrapper((HttpServletRequest) servletRequest);
                commonLoggingResponseWrapper = new CommonLoggingResponseWrapper((HttpServletResponse) servletResponse);
            } catch (Exception throwable) {
                log.warn("Failed to create request response wrapper, {}", throwable.getMessage(), throwable);
            }

            if (isLoggingReqRes && !isHealthCheckEndpoint && commonLoggingRequestWrapper != null) {
                CommonLoggingRequest commonLoggingRequest = new CommonLoggingRequest(commonLoggingRequestWrapper);
                log.info("[Request]: {}", objectMapper.writeValueAsString(commonLoggingRequest));
            }

            if (commonLoggingRequestWrapper != null && commonLoggingResponseWrapper != null) {
                filterChain.doFilter(commonLoggingRequestWrapper, commonLoggingResponseWrapper);
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        } finally {
            if (commonLoggingResponseWrapper != null) {
                CommonLoggingResponse commonLoggingResponse = new CommonLoggingResponse(commonLoggingResponseWrapper);
                boolean hasHttpError = HttpStatusUtil.isError(commonLoggingResponse.getStatus());
                if (isHealthCheckEndpoint && hasHttpError) {
                    log.info("Server Health Check is down");
                }
                if (isLoggingReqRes && !isHealthCheckEndpoint || isHealthCheckEndpoint && hasHttpError) {
                    log.info("[Response]: {}", objectMapper.writeValueAsString(commonLoggingResponse));
                }
            }

            MDC.remove(CommonLoggingConstants.MDC_CORRELATION_ID);
        }

        log.info("Exiting CommonLoggingFilter");
    }

    @Override
    public void destroy() {
        log.info("Destroying CommonLoggingFilter");
    }
}
