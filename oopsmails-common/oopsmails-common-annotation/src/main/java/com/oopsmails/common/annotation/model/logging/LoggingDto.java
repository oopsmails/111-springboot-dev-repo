package com.oopsmails.common.annotation.model.logging;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class LoggingDto {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss:SSSS")
    private LocalDateTime timestamp;

    private AppLogLevel level;

    private String component;

    private String correlationId;

    private String sessionId;

    private String message;

    public LoggingDto() {
        this.timestamp = LocalDateTime.now();
        this.message = "";
    }
}
