package com.oopsmails.common.domain.logging;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.oopsmails.common.domain.json.LocalDateTimeDeserializer;
import com.oopsmails.common.domain.json.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class LoggingDto {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss:SSSS")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class) // as example
    @JsonSerialize(using = LocalDateTimeSerializer.class) // as example
    private LocalDateTime timestamp;

    private String component;

    private String correlationId;

    private String sessionId;

    private String message;

    public LoggingDto() {
        this.timestamp = LocalDateTime.now();
        this.message = "";
    }
}
