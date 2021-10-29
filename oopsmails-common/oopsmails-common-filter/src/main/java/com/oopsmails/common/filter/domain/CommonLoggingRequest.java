package com.oopsmails.common.filter.domain;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.oopsmails.common.filter.util.CommonLoggingUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CommonLoggingRequest {
    private String uri;

    private String method;

    private String contentType;

    private List<CommonLoggingKeyValue> headers;
    private List<CommonLoggingKeyValue> parameters;

    @JsonRawValue
    private String body;

    public CommonLoggingRequest(CommonLoggingRequestWrapper commonLoggingRequestWrapper) {
        this.uri = commonLoggingRequestWrapper.getRequestURI();
        this.method = commonLoggingRequestWrapper.getMethod();
        this.contentType = commonLoggingRequestWrapper.getContentType();
        this.headers = CommonLoggingUtil.getHeaders(commonLoggingRequestWrapper);
        this.parameters = CommonLoggingUtil.getParameters(commonLoggingRequestWrapper);
        this.body = CommonLoggingUtil.getBody(commonLoggingRequestWrapper);
    }
}
