package com.oopsmails.common.logging.domain;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.oopsmails.common.logging.util.GeneralLoggingUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GeneralLoggingResponse {
    private int status;
    private String contentType;
    private List<LoggingKeyValue> headers;

    @JsonRawValue
    private String body;

    public GeneralLoggingResponse(GeneralLoggingResponseWrapper generalLoggingResponseWrapper) {
        this.status = generalLoggingResponseWrapper.getStatus();
        this.contentType = generalLoggingResponseWrapper.getContentType();
        this.headers = GeneralLoggingUtils.getHeaders(generalLoggingResponseWrapper);
        this.body = GeneralLoggingUtils.getBody(generalLoggingResponseWrapper);
    }
}
