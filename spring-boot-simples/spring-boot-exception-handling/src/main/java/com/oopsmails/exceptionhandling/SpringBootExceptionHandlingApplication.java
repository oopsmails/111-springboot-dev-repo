package com.oopsmails.exceptionhandling;

import com.oopsmails.common.filter.config.OopsmailsCommonLoggingConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({OopsmailsCommonLoggingConfig.class})
public class SpringBootExceptionHandlingApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootExceptionHandlingApplication.class, args);
    }

}
