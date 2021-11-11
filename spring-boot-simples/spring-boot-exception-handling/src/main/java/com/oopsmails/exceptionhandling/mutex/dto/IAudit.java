package com.oopsmails.exceptionhandling.mutex.dto;

import java.sql.Timestamp;
import java.util.Date;

public interface IAudit {
    String getCreatedBy();
    void setCreatedBy(String createdBy);

    Date getCreatedDate();
    void setCreatedDate(Date createdDate);

    String getLastUpdatedBy();
    void setLastUpdatedBy(String updatedBy);

    Date getLastUpdatedDate();
    void setLastUpdatedDate(Date updatedDate);
}
