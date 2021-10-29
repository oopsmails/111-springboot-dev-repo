package com.oopsmails.common.annotation.model.logging;

import lombok.Getter;

public enum AppLogLevel {
    APPLICATION ("APPLICATION"),
    PERFORMANCE ("PERFORMANCE"),
    AUDIT("AUDIT");

    @Getter
    private String appender; // appender name defined in logback.xml

    private AppLogLevel(String appender) {
        this.appender = appender;
    }
}
