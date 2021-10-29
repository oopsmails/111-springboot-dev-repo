package com.oopsmails.common.annotation.audit;

import com.oopsmails.common.annotation.model.logging.LoggingOrigin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.PARAMETER, ElementType.FIELD})
// only METHOD now as example, will add others
public @interface LoggingAudit {
    LoggingOrigin origin();

    String message() default "";

    String type() default "";
}
