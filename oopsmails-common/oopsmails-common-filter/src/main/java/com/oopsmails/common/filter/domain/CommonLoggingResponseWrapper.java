package com.oopsmails.common.filter.domain;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class CommonLoggingResponseWrapper extends HttpServletResponseWrapper {

    ByteArrayOutputStream byteArrayOutputStream;
    private CommonLoggingServletOutputStream commonLoggingServletOutputStream;
    private PrintWriter printWriter;

    public CommonLoggingResponseWrapper(HttpServletResponse httpServletResponse) {
        super(httpServletResponse);
    }

    public String getContent() {
        return byteArrayOutputStream == null ? "" : byteArrayOutputStream.toString();
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        if (this.printWriter == null) {
            this.printWriter = new PrintWriter(new OutputStreamWriter(getOutputStream()));
        }

        return this.printWriter;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if (this.commonLoggingServletOutputStream == null) {
            this.byteArrayOutputStream = new ByteArrayOutputStream();
            this.commonLoggingServletOutputStream = new CommonLoggingServletOutputStream(getResponse().getOutputStream(), byteArrayOutputStream);
        }

        return this.commonLoggingServletOutputStream;
    }

}
