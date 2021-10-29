package com.oopsmails.common.logging.util;

import com.oopsmails.common.logging.domain.GeneralLoggingRequestWrapper;
import com.oopsmails.common.logging.domain.GeneralLoggingResponseWrapper;
import com.oopsmails.common.logging.domain.LoggingKeyValue;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;

@Slf4j
public class GeneralLoggingUtils {
    private GeneralLoggingUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static List<LoggingKeyValue> getHeaders(GeneralLoggingRequestWrapper generalLoggingRequestWrapper) {
        List<LoggingKeyValue> result = new ArrayList<>();
        Enumeration<String> keys = generalLoggingRequestWrapper.getHeaderNames();
        while (keys != null && keys.hasMoreElements()) {
            String key = keys.nextElement();
            if (key.equalsIgnoreCase("authorization")) {
                continue;
            }

            Enumeration<String> values = generalLoggingRequestWrapper.getHeaders(key);
            while (values.hasMoreElements()) {
                String value = values.nextElement();

                LoggingKeyValue loggingKeyValue = new LoggingKeyValue();
                loggingKeyValue.setKey(key);
                loggingKeyValue.setValue(value);
                result.add(loggingKeyValue);
            }
        }

        return result;
    }

    public static List<LoggingKeyValue> getParameters(GeneralLoggingRequestWrapper generalLoggingRequestWrapper) {
        List<LoggingKeyValue> result = new ArrayList<>();
        Enumeration<String> keys = generalLoggingRequestWrapper.getParameterNames();
        while (keys != null && keys.hasMoreElements()) {
            String key = keys.nextElement();

            Enumeration<String> values = generalLoggingRequestWrapper.getHeaders(key);
            while (values.hasMoreElements()) {
                String value = values.nextElement();

                LoggingKeyValue loggingKeyValue = new LoggingKeyValue();
                loggingKeyValue.setKey(key);
                loggingKeyValue.setValue(value);
                result.add(loggingKeyValue);
            }
        }

        return result;
    }

    public static String getBody(GeneralLoggingRequestWrapper generalLoggingRequestWrapper) {
        try {
            if (generalLoggingRequestWrapper.getInputStream() == null) {
                return "";
            }

            return IOUtils.toString(generalLoggingRequestWrapper.getInputStream(), StandardCharsets.UTF_8.name());
        } catch (IOException ioException) {
            log.error("Failed to read request body, {}.", ioException.getMessage(), ioException);
            return "";
        }
    }

    public static List<LoggingKeyValue> getHeaders(GeneralLoggingResponseWrapper generalLoggingResponseWrapper) {
        List<LoggingKeyValue> result = new ArrayList<>();
        Collection<String> keys = generalLoggingResponseWrapper.getHeaderNames();

        keys.forEach(key -> {
            Collection<String> values = generalLoggingResponseWrapper.getHeaders(key);
            values.forEach(value -> {
                LoggingKeyValue loggingKeyValue = new LoggingKeyValue();
                loggingKeyValue.setKey(key);
                loggingKeyValue.setValue(value);
                result.add(loggingKeyValue);
            });
        });

        return result;
    }

    public static String getBody(GeneralLoggingResponseWrapper generalLoggingResponseWrapper) {
//        try {
//            return generalLoggingResponseWrapper.getContent();
//        } catch (IOException ioException) {
//            log.error("Failed to read response body, {}.", ioException.getMessage(), ioException);
//            return "";
//        }
        return generalLoggingResponseWrapper.getContent();
    }
}
