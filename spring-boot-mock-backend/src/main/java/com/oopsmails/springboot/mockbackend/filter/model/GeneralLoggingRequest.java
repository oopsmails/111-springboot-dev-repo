package com.oopsmails.springboot.mockbackend.filter.model;

import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GeneralLoggingRequest {
    private String uri;

    private String method;

    private String contentType;

    private List<LoggingKeyValue> headers;
    private List<LoggingKeyValue> parameters;

    @JsonRawValue
    private String body;

    public GeneralLoggingRequest(GeneralLoggingRequestWrapper generalLoggingRequestWrapper) {
        this.uri = generalLoggingRequestWrapper.getRequestURI();
        this.method = generalLoggingRequestWrapper.getMethod();
        this.contentType = generalLoggingRequestWrapper.getContentType();
        this.headers = GeneralLoggingUtils.getHeaders(generalLoggingRequestWrapper);
        this.parameters = GeneralLoggingUtils.getParameters(generalLoggingRequestWrapper);
        this.body = GeneralLoggingUtils.getBody(generalLoggingRequestWrapper);
    }
}
