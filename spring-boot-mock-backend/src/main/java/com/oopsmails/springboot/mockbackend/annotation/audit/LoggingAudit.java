package com.oopsmails.springboot.mockbackend.annotation.audit;

import com.oopsmails.springboot.mockbackend.model.logging.LoggingOrigin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LoggingAudit {
    LoggingOrigin origin();
    String message() default "";
}
