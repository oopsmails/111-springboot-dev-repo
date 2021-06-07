package com.oopsmails.springboot.mockbackend.filter.model;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class GeneralLoggingResponseWrapper extends HttpServletResponseWrapper {

    ByteArrayOutputStream byteArrayOutputStream;
    private GeneralLoggingServletOutputStream generalLoggingServletOutputStream;
    private PrintWriter printWriter;

    public GeneralLoggingResponseWrapper(HttpServletResponse httpServletResponse) {
        super(httpServletResponse);
    }

    public String getContent() throws IOException {
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
        if (this.generalLoggingServletOutputStream == null) {
            this.byteArrayOutputStream = new ByteArrayOutputStream();
            this.generalLoggingServletOutputStream = new GeneralLoggingServletOutputStream(getResponse().getOutputStream(), byteArrayOutputStream);
        }

        return this.generalLoggingServletOutputStream;
    }

}
