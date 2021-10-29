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
import java.util.Set;

@Slf4j
@Component
@Order()
public class GeneralRedirectFilter implements Filter {
    @Value("${generic.redirect.enabled:true}")
    private boolean isGenericRedirectEnabled;

    @Value("#{'${generic.redirect.url.exclude}'.split(',')}")
    private Set<String> redirectExemptionUrls;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String path = req.getRequestURI();
        log.info("doFilter(), req.getRequestURI(): {}", path);

        if (!isGenericRedirectEnabled || inExemption(path, redirectExemptionUrls)) {
            log.warn("Skipping url redirecting, isGenericRedirectEnabled = {} or {} is inExemption", isGenericRedirectEnabled, path);
            chain.doFilter(request, response);
            return;
        }

        if (isGenericRedirectEnabled) {
            if (path.indexOf("/ping") == 0) {
                log.warn("doFilter(), /ping, no change, but will hit /ping in Controller.");
                req = new HttpServletRequestWrapper((HttpServletRequest) request) {
                    @Override
                    public String getRequestURI() {
                        return "/ping"; // example of no change
                    }
                };
            } else if (path.indexOf("/backendmock/") == 0) {
                // no ops
                log.warn("doFilter(), /backendmock, no change.");
            } else if (path.indexOf("/genericmock/") == 0) {
                req = new HttpServletRequestWrapper((HttpServletRequest) request) {
                    @Override
                    public String getRequestURI() {
                        return "/";
                    }
                };
                log.warn("doFilter(), /genericmock redirecting to generic url: [{}]", req.getRequestURI());
            } else if (path.indexOf("/") == 0) {
                req = new HttpServletRequestWrapper((HttpServletRequest) request) {
                    @Override
                    public String getRequestURI() {
                        return "/";
                    }
                };
                log.warn("doFilter(), / redirecting to generic url: [{}]", req.getRequestURI());
            }
        }

        chain.doFilter(req, res);
    }

    private static boolean inExemption(final String path, Set<String> loggingExemptionUrls) {
        for(String url : loggingExemptionUrls) {
            if (path.indexOf(url) >= 0) {
                return true;
            }
        }
        return false;
    }
}
