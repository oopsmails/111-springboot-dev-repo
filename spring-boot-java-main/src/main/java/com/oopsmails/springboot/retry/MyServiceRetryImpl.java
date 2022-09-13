package com.oopsmails.springboot.retry;

import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class MyServiceRetryImpl implements MyServiceRetry {
    @Override
    public void retryService(String sql) {
//        throw new SQLException("mock for retry!");
        sql.substring(0, 5); // throwing Exception for testing
    }

    @Override
    public void retryServiceWithRecovery(String sql) throws SQLException {

    }

    @Override
    public void recover(SQLException e, String sql) {

    }

    @Override
    public void retryServiceWithExternalConfiguration(String sql) throws SQLException {

    }
}
