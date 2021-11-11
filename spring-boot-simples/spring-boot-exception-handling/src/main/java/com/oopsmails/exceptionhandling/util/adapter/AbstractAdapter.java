package com.oopsmails.exceptionhandling.util.adapter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oopsmails.exceptionhandling.mutex.dto.IAudit;
import com.oopsmails.exceptionhandling.mutex.dto.IVersion;
import com.oopsmails.exceptionhandling.mutex.entity.IEntityAudit;
import com.oopsmails.exceptionhandling.mutex.entity.IEntityVersion;

import java.sql.Date;
import java.sql.Timestamp;

public class AbstractAdapter {
    private final static ObjectMapper objectMapperNotFailOnUnknownProperties
            = new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

    protected static ObjectMapper getObjectMapper() {
        return objectMapperNotFailOnUnknownProperties;
    }

    protected static void setEntityVersion(IVersion version, IEntityVersion entityVersion) {
        entityVersion.setVersion(version.getVersion());
    }

    protected static void setVersion(IEntityVersion entityVersion, IVersion version) {
        version.setVersion(entityVersion.getVersion());
    }

    protected static void setEntityAudit(IAudit audit, IEntityAudit entityAudit) {
        entityAudit.setCreatedBy(audit.getCreatedBy());
        entityAudit.setLastUpdatedBy(audit.getLastUpdatedBy());
        if (audit.getCreatedDate() != null) {
            entityAudit.setCreatedDateTs(new Timestamp(audit.getCreatedDate().getTime()));
        }
        if (audit.getLastUpdatedDate() != null) {
            entityAudit.setLastUpdatedDateTs(new Timestamp(audit.getLastUpdatedDate().getTime()));
        }
    }

    protected static void setAudit(IEntityAudit entityAudit, IAudit audit) {
        audit.setCreatedBy(entityAudit.getCreatedBy());
        audit.setLastUpdatedBy(entityAudit.getLastUpdatedBy());
        audit.setCreatedDate(Date.from(entityAudit.getCreatedDateTs().toInstant()));
        audit.setLastUpdatedDate(Date.from(entityAudit.getLastUpdatedDateTs().toInstant()));
    }
}
