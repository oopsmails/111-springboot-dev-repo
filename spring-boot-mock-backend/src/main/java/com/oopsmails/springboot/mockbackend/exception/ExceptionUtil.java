package com.oopsmails.springboot.mockbackend.exception;

import org.springframework.http.HttpStatus;

public class ExceptionUtil {
    public static String getRootCause(Throwable throwable) {
        if (throwable == null) {
            return null;
        }
        Throwable cause = throwable.getCause();
        if (cause == null) {
            return throwable.getMessage();
        }
        return getRootCause(cause);
    }

    public static OopsException getOopsCause(Throwable throwable) {
        if (throwable == null) {
            return null;
        }
        if (throwable instanceof OopsException) {
            return (OopsException) throwable;
        }
        Throwable cause = throwable.getCause();
        if (cause == null) {
            return null;
        }
        return getOopsCause(cause);
    }

//    public static boolean isClientRequestError(ApiException apiException) {
//        int httpStatusCode = apiException.getStatusCode();
//        // 400 <= httpStatusCode < 500
//        return HttpStatus.BAD_REQUEST.value() <= httpStatusCode && httpStatusCode < HttpStatus.INTERNAL_SERVER_ERROR.value();
//    }
}
