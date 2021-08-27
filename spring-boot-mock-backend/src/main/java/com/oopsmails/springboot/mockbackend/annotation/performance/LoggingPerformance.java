package com.oopsmails.springboot.mockbackend.annotation.performance;

import com.oopsmails.springboot.mockbackend.model.logging.LoggingOrigin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LoggingPerformance {
    LoggingOrigin origin();

    String transaction() default "";

    String message() default "";
}
