package com.oopsmails.exceptionhandling.mutex.entity;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.sql.Timestamp;
import java.time.Instant;

public class EntityAuditListener {

    /**
     * Called before every Insertion
     */
    @PrePersist
    protected void onCreate(IEntityAudit entityAudit) {
        Timestamp timestamp = this.getCurrentTimestamp();
        if (entityAudit.getCreatedDateTs() == null) {
            entityAudit.setCreatedDateTs(timestamp);
        }

        entityAudit.setLastUpdatedDateTs(timestamp);
    }

    /**
     * Called before every Update
     */
    @PreUpdate
    protected void onUpdate(IEntityAudit entityAudit) {
        entityAudit.setLastUpdatedDateTs(this.getCurrentTimestamp());
    }


    private Timestamp getCurrentTimestamp() {
        Instant instant = Instant.now();
        return new Timestamp(java.util.Date.from(instant).getTime());
    }
}
