package com.oopsmails.springboot.mockbackend.filter.model;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class GeneralLoggingServletInputStream extends ServletInputStream {

    private final ByteArrayInputStream byteArrayInputStream;

    public GeneralLoggingServletInputStream(byte[] content) {
        this.byteArrayInputStream = new ByteArrayInputStream(content);
    }

    @Override
    public boolean isFinished() {
        return this.byteArrayInputStream.available() == 0;
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setReadListener(ReadListener listener) {
        // Do nothing here
    }

    @Override
    public int read() throws IOException {
        return byteArrayInputStream.read();
    }

}
