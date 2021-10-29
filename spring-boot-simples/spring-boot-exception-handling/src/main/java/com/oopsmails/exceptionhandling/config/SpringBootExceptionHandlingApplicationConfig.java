package com.oopsmails.exceptionhandling.config;

import com.oopsmails.common.logging.filter.GeneralLoggingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootConfiguration
@ComponentScan("com.oopsmails.common.logging.filter")
public class SpringBootExceptionHandlingApplicationConfig {
//    @Autowired
//    GeneralLoggingFilter generalLoggingFilter;
}
