package com.oopsmails.common.annotation.model.logging;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoggingDtoPerformance extends LoggingDto {
    private long duration;

    private String status;

    private String signature;

    private String thread;

    private LoggingOrigin origin;

    public LoggingDtoPerformance() {
        super();
        this.setLevel(AppLogLevel.PERFORMANCE);
    }
}
