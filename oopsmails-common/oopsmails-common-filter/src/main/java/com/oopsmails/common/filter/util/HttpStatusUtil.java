package com.oopsmails.common.filter.util;

public class HttpStatusUtil {
    private HttpStatusUtil() {

    }

    public enum HttpStatusEnum {
        INFORMATIONAL_RESPONSE(1),
        SUCCESS(2),
        REDIRECTION(3),
        CLIENT_ERROR(4),
        SERVER_ERROR(5);

        private final int value;

        HttpStatusEnum(int value) {
            this.value = value;
        }

        public static HttpStatusEnum valueOf(int value) {
            HttpStatusEnum result = getStatus(value);

            if (result == null) {
                throw new IllegalArgumentException("No matching for code [" + value + "]");
            } else {
                return result;
            }
        }

        public static HttpStatusEnum getStatus(int status) {
            int enumCode = status / 100;

            for (HttpStatusEnum httpStatusEnum : values()) {
                if (httpStatusEnum.value() == enumCode) {
                    return httpStatusEnum;
                }
            }

            return null;
        }

        public int value() {
            return this.value;
        }
    }

    public static  boolean is400LevelError(int status) {
        return HttpStatusEnum.CLIENT_ERROR == HttpStatusEnum.valueOf(status);
    }

    public static  boolean is500LevelError(int status) {
        return HttpStatusEnum.SERVER_ERROR == HttpStatusEnum.valueOf(status);
    }

    public static boolean isError(int status) {
        return is400LevelError(status) || is500LevelError(status);
    }
}
