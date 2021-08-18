package com.oopsmails.springboot.mockbackend.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

@Slf4j
@Component
@Order()
public class GeneralRedirectFilter implements Filter {
    @Value("${generic.redirect.enabled}")
    private boolean isGenericRedirectEnabled;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String path = req.getRequestURI();
        log.info("doFilter(), req.getRequestURI(): {}", path);

        if (isGenericRedirectEnabled) {
            if (path.indexOf("/ping") == 0) {
                req = new HttpServletRequestWrapper((HttpServletRequest) request) {
                    @Override
                    public String getRequestURI() {
                        return "/ping"; // example of no change
                    }
                };
            } else if (path.indexOf("/backendmock/") == 0) {
                // no ops
            } else if (path.indexOf("/genericmock/") == 0) {
                req = new HttpServletRequestWrapper((HttpServletRequest) request) {
                    @Override
                    public String getRequestURI() {
                        return "/";
                    }
                };
            } else if (path.indexOf("/") == 0) {
                req = new HttpServletRequestWrapper((HttpServletRequest) request) {
                    @Override
                    public String getRequestURI() {
                        return "/";
                    }
                };
            }

            log.warn("doFilter(), redirecting to generic url: [{}]", req.getRequestURI());
        }

        chain.doFilter(req, res);
    }
}
