package com.oopsmails.exceptionhandling.mutex.adapter;

import com.oopsmails.exceptionhandling.mutex.dto.Mutex;
import com.oopsmails.exceptionhandling.mutex.entity.EntityMutex;
import com.oopsmails.exceptionhandling.util.adapter.AbstractAdapter;

import java.sql.Timestamp;

public class MutexAdapter extends AbstractAdapter {
    public static Mutex toMutex(EntityMutex entityMutex) {
        Mutex mutex = new Mutex();

        mutex.setResourceCode(entityMutex.getResourceCd());
        mutex.setNodeId(entityMutex.getNodeId());
        mutex.setLockUntil(entityMutex.getLockUntil().toLocalDateTime());

        setAudit(entityMutex, mutex);
        setVersion(entityMutex, mutex);

        return mutex;
    }

    public static EntityMutex toEntityMutex(Mutex mutex) {
        EntityMutex entityMutex = new EntityMutex();

        entityMutex.setResourceCd(mutex.getResourceCode());
        entityMutex.setNodeId(mutex.getNodeId());
        entityMutex.setLockUntil(Timestamp.valueOf(mutex.getLockUntil()));

        setEntityAudit(mutex, entityMutex);
        setEntityVersion(mutex, entityMutex);

        return entityMutex;
    }
}
