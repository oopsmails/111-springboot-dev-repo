package com.oopsmails.springboot.retry;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;

import java.sql.SQLException;

public interface MyServiceRetry {

    @Retryable(value = RuntimeException.class)
    void retryService(String sql);

    @Retryable(value = SQLException.class)
    void retryServiceWithRecovery(String sql) throws SQLException;

    @Recover
    void recover(SQLException e, String sql);

    @Retryable(value = SQLException.class, maxAttemptsExpression = "${retry.maxAttempts}",
            backoff = @Backoff(delayExpression = "${retry.maxDelay}"))
    void retryServiceWithExternalConfiguration(String sql) throws SQLException;
}
