package com.oopsmails.common.filter;

import com.oopsmails.common.constant.CommonLoggingConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Component
@Order()
public class CommonRequestFilter implements Filter {

    private static String getOrGenerateCorrelationId(MutableHttpServletRequest servletRequest) {
        String result = servletRequest.getHeader(CommonLoggingConstants.REQUEST_HEADER_CORRELATION_ID);
        if (StringUtils.isBlank(result)) {
            result = UUID.randomUUID().toString();
            log.warn("No correlation_id found in Request Header, adding {}.", result);
        }

        return result;
    }

    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        MutableHttpServletRequest mutableRequest = new MutableHttpServletRequest(httpRequest);

        String correlationId = getOrGenerateCorrelationId(mutableRequest);
        mutableRequest.putHeader(CommonLoggingConstants.REQUEST_HEADER_CORRELATION_ID, correlationId);
        MDC.put(CommonLoggingConstants.MDC_CORRELATION_ID, correlationId);
        log.info("Setting correlation_id in Response, [{}]", correlationId);
        httpResponse.setHeader(CommonLoggingConstants.REQUEST_HEADER_CORRELATION_ID, correlationId);

        chain.doFilter(mutableRequest, response);
    }

    /**
     * To add correlation_id if there is no correlation_id in Header
     * <p>
     * Ref: https://stackoverflow.com/questions/48240291/adding-custom-header-to-request-via-filter
     */
    private final class MutableHttpServletRequest extends HttpServletRequestWrapper {
        // holds custom header and value mapping
        private final Map<String, String> customHeaders;

        public MutableHttpServletRequest(HttpServletRequest request) {
            super(request);
            this.customHeaders = new HashMap<String, String>();
        }

        public void putHeader(String name, String value) {
            this.customHeaders.put(name, value);
        }

        public String getHeader(String name) {
            // check the custom headers first
            String headerValue = customHeaders.get(name);

            if (headerValue != null) {
                return headerValue;
            }
            // else return from into the original wrapped object
            return ((HttpServletRequest) getRequest()).getHeader(name);
        }

        public Enumeration<String> getHeaderNames() {
            // create a set of the custom header names
            Set<String> set = new HashSet<String>(customHeaders.keySet());

            // now add the headers from the wrapped request object
            @SuppressWarnings("unchecked")
            Enumeration<String> e = ((HttpServletRequest) getRequest()).getHeaderNames();
            while (e.hasMoreElements()) {
                // add the names of the request headers into the list
                String n = e.nextElement();
                set.add(n);
            }

            // create an enumeration from the set and return
            return Collections.enumeration(set);
        }
    }
}
