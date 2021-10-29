package com.oopsmails.common.annotation.model.logging;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoggingDtoAudit extends LoggingDto {
    private long duration;

    private String userId;

    private String target;

    private ClientType clientType;

    private boolean success;

    public LoggingDtoAudit() {
        super();
        this.setLevel(AppLogLevel.AUDIT);
    }
}
