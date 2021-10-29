package com.oopsmails.exceptionhandling.config;

import com.oopsmails.common.annotation.config.OopsmailsCommonAnnotationConfig;
import com.oopsmails.common.filter.config.OopsmailsCommonLoggingConfig;
import com.oopsmails.common.filter.CommonLoggingFilter;
import com.oopsmails.common.filter.CommonRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;

import javax.servlet.DispatcherType;
import java.util.Collections;

@SpringBootConfiguration
@Import({
        OopsmailsCommonLoggingConfig.class,
        OopsmailsCommonAnnotationConfig.class
})
public class SpringBootExceptionHandlingApplicationConfig {
    @Autowired
    private CommonRequestFilter commonRequestFilter;

    @Autowired
    private CommonLoggingFilter commonLoggingFilter;

    @Bean
    public FilterRegistrationBean<CommonRequestFilter> commonRequestFilterRegistration() {
        FilterRegistrationBean<CommonRequestFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(commonRequestFilter);
        filterRegistrationBean.setUrlPatterns(Collections.singletonList("/*"));
        filterRegistrationBean.setDispatcherTypes(DispatcherType.REQUEST);
        filterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE + 1);
        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean<CommonLoggingFilter> generalLoggingFilterRegistration() {
        FilterRegistrationBean<CommonLoggingFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(commonLoggingFilter);
        filterRegistrationBean.setUrlPatterns(Collections.singletonList("/*"));
        filterRegistrationBean.setDispatcherTypes(DispatcherType.REQUEST);
        filterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE + 2);
        return filterRegistrationBean;
    }
}
