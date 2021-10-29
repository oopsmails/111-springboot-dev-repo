package com.oopsmails.common.annotation.performance;

import com.oopsmails.common.annotation.model.logging.LoggingOrigin;

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
