package com.oopsmails.exceptionhandling.mutex.service;

import com.oopsmails.exceptionhandling.mutex.dto.Mutex;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class TestUsageAsyncMutexService {

    @Autowired
    private IMutexService mutexService;

    @Async
    public void update(String resourceCode, String nodeId) throws InterruptedException {
        Mutex mutex = this.mutexService.findByResourceCode(resourceCode);
        mutex.setNodeId(nodeId);
        this.mutexService.save(mutex);

        log.info("Mutex with resourceCode {} is updated with nodeId {}.", resourceCode, nodeId);
    }

    @Async
    public void create(String resourceCode, String nodeId) throws InterruptedException {
        Mutex mutex = new Mutex();
        mutex.setResourceCode(resourceCode);
        mutex.setNodeId(nodeId);
        mutex.setLockUntil(LocalDateTime.now());
        this.mutexService.save(mutex);

        log.info("Mutex with resourceCode {} is saved with nodeId {}.", resourceCode, nodeId);
    }
}
