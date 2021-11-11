package com.oopsmails.exceptionhandling.mutex.service;

import com.oopsmails.exceptionhandling.mutex.entity.IEntityAudit;

public interface IAuditService {
    void audit(IEntityAudit entityAudit);
}
