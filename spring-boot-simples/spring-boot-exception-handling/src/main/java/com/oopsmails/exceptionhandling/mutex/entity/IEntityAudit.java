package com.oopsmails.exceptionhandling.mutex.entity;

import java.sql.Timestamp;

/**
 * Use this in Entity layer
 */
public interface IEntityAudit {
    String getCreatedBy();
    void setCreatedBy(String createdBy);

    Timestamp getCreatedDateTs();
    void setCreatedDateTs(Timestamp createdDateTs);

    String getLastUpdatedBy();
    void setLastUpdatedBy(String updatedBy);

    Timestamp getLastUpdatedDateTs();
    void setLastUpdatedDateTs(Timestamp updatedDateTs);
}
