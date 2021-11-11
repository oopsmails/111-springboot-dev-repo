package com.oopsmails.exceptionhandling.mutex.service;

import com.oopsmails.exceptionhandling.mutex.entity.IEntityAudit;
import org.springframework.stereotype.Service;

@Service
public class AuditServiceImpl implements IAuditService {

    @Override
    public void audit(IEntityAudit entityAudit) {
        final String auditUser = "NeedOtherServiceToGetUser ForAudit";
        if (entityAudit.getCreatedBy() == null) {
            entityAudit.setCreatedBy(auditUser);
        }

        entityAudit.setLastUpdatedBy(auditUser);
    }
}
