package com.oopsmails.exceptionhandling.mutex.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * This class could be abstracted another super class as AbstractAudit because not all domain objects need version.
 */

@Getter
@Setter
public abstract class AbstractAuditAndVersion implements IAudit, IVersion, Serializable {
    private static final long serialVersionUID = 1L;

    private String createdBy;
    private Date createdDate;

    private String lastUpdatedBy;
    private Date lastUpdatedDate;

    private Integer version;
}
