package com.oopsmails.common.filter.domain;

import org.apache.commons.io.output.TeeOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import java.io.IOException;
import java.io.OutputStream;

public class CommonLoggingServletOutputStream extends ServletOutputStream {

    private final TeeOutputStream teeOutputStream;

    private final ServletOutputStream servletOutputStream;

    public CommonLoggingServletOutputStream(ServletOutputStream servletOutputStream, OutputStream outputStream) {
        this.teeOutputStream = new TeeOutputStream(servletOutputStream, outputStream);
        this.servletOutputStream = servletOutputStream;
    }

    @Override
    public void write(byte[] bytes) throws IOException {
        this.teeOutputStream.write(bytes);
    }

    @Override
    public void write(byte[] bytes, int off, int len) throws IOException {
        this.teeOutputStream.write(bytes, off, len);
    }

    @Override
    public void flush() throws IOException {
        this.teeOutputStream.flush();
    }

    @Override
    public void write(int b) throws IOException {
        this.teeOutputStream.write(b);
    }

    @Override
    public void close() throws IOException {
        super.close();
        this.teeOutputStream.close();
    }

    @Override
    public boolean isReady() {
        return this.servletOutputStream.isReady();
    }

    @Override
    public void setWriteListener(WriteListener listener) {
        this.servletOutputStream.setWriteListener(listener);
    }
}
