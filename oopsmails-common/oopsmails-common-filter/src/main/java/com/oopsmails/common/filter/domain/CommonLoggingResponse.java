package com.oopsmails.common.filter.domain;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.oopsmails.common.filter.util.CommonLoggingUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CommonLoggingResponse {
    private int status;
    private String contentType;
    private List<CommonLoggingKeyValue> headers;

    @JsonRawValue
    private String body;

    public CommonLoggingResponse(CommonLoggingResponseWrapper commonLoggingResponseWrapper) {
        this.status = commonLoggingResponseWrapper.getStatus();
        this.contentType = commonLoggingResponseWrapper.getContentType();
        this.headers = CommonLoggingUtil.getHeaders(commonLoggingResponseWrapper);
        this.body = CommonLoggingUtil.getBody(commonLoggingResponseWrapper);
    }
}
