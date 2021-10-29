package com.oopsmails.common.filter.domain;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

@Slf4j
public class CommonLoggingRequestWrapper extends HttpServletRequestWrapper {

    private final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

    public CommonLoggingRequestWrapper(HttpServletRequest httpServletRequest) throws IOException {
        super(httpServletRequest);

        IOUtils.copy(super.getInputStream(), byteArrayOutputStream);
        log.info("Total {} bytes copied from httpServletRequest", this.byteArrayOutputStream.size());
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new CommonLoggingServletInputStream(this.byteArrayOutputStream.toByteArray());
    }

    @Override
    public BufferedReader getReader() throws IOException {
        String encoding = getCharacterEncoding();
        if (encoding == null) {
            encoding = "UTF-8";
        }

        return new BufferedReader(new InputStreamReader(getInputStream(), encoding));
    }

}
