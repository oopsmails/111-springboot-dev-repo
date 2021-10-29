package com.oopsmails.common.filter.util;

import com.oopsmails.common.filter.domain.CommonLoggingRequestWrapper;
import com.oopsmails.common.filter.domain.CommonLoggingResponseWrapper;
import com.oopsmails.common.filter.domain.CommonLoggingKeyValue;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;

@Slf4j
public class CommonLoggingUtil {
    private CommonLoggingUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static List<CommonLoggingKeyValue> getHeaders(CommonLoggingRequestWrapper commonLoggingRequestWrapper) {
        List<CommonLoggingKeyValue> result = new ArrayList<>();
        Enumeration<String> keys = commonLoggingRequestWrapper.getHeaderNames();
        while (keys != null && keys.hasMoreElements()) {
            String key = keys.nextElement();
            if (key.equalsIgnoreCase("authorization")) {
                continue;
            }

            Enumeration<String> values = commonLoggingRequestWrapper.getHeaders(key);
            while (values.hasMoreElements()) {
                String value = values.nextElement();

                CommonLoggingKeyValue commonLoggingKeyValue = new CommonLoggingKeyValue();
                commonLoggingKeyValue.setKey(key);
                commonLoggingKeyValue.setValue(value);
                result.add(commonLoggingKeyValue);
            }
        }

        return result;
    }

    public static List<CommonLoggingKeyValue> getParameters(CommonLoggingRequestWrapper commonLoggingRequestWrapper) {
        List<CommonLoggingKeyValue> result = new ArrayList<>();
        Enumeration<String> keys = commonLoggingRequestWrapper.getParameterNames();
        while (keys != null && keys.hasMoreElements()) {
            String key = keys.nextElement();

            Enumeration<String> values = commonLoggingRequestWrapper.getHeaders(key);
            while (values.hasMoreElements()) {
                String value = values.nextElement();

                CommonLoggingKeyValue commonLoggingKeyValue = new CommonLoggingKeyValue();
                commonLoggingKeyValue.setKey(key);
                commonLoggingKeyValue.setValue(value);
                result.add(commonLoggingKeyValue);
            }
        }

        return result;
    }

    public static String getBody(CommonLoggingRequestWrapper commonLoggingRequestWrapper) {
        try {
            if (commonLoggingRequestWrapper.getInputStream() == null) {
                return "";
            }

            return IOUtils.toString(commonLoggingRequestWrapper.getInputStream(), StandardCharsets.UTF_8.name());
        } catch (IOException ioException) {
            log.error("Failed to read request body, {}.", ioException.getMessage(), ioException);
            return "";
        }
    }

    public static List<CommonLoggingKeyValue> getHeaders(CommonLoggingResponseWrapper commonLoggingResponseWrapper) {
        List<CommonLoggingKeyValue> result = new ArrayList<>();
        Collection<String> keys = commonLoggingResponseWrapper.getHeaderNames();

        keys.forEach(key -> {
            Collection<String> values = commonLoggingResponseWrapper.getHeaders(key);
            values.forEach(value -> {
                CommonLoggingKeyValue commonLoggingKeyValue = new CommonLoggingKeyValue();
                commonLoggingKeyValue.setKey(key);
                commonLoggingKeyValue.setValue(value);
                result.add(commonLoggingKeyValue);
            });
        });

        return result;
    }

    public static String getBody(CommonLoggingResponseWrapper commonLoggingResponseWrapper) {
//        try {
//            return commonLoggingResponseWrapper.getContent();
//        } catch (IOException ioException) {
//            log.error("Failed to read response body, {}.", ioException.getMessage(), ioException);
//            return "";
//        }
        return commonLoggingResponseWrapper.getContent();
    }
}
