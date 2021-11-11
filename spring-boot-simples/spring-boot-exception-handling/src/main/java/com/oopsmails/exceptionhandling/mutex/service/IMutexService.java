package com.oopsmails.exceptionhandling.mutex.service;

import com.oopsmails.exceptionhandling.mutex.dto.Mutex;

import java.time.Duration;
import java.time.LocalDateTime;

public interface IMutexService {
    boolean lock(String resourceCd, Duration duration);

    boolean release(String resourceCd);

    Mutex save(Mutex mutex);

    Mutex findByResourceCode(String resourceCode);

    void setHostName(String hostName);
}
