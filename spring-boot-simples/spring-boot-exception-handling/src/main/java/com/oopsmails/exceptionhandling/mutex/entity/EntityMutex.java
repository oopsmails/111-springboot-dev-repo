package com.oopsmails.exceptionhandling.mutex.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.LockModeType;
import javax.persistence.NamedQuery;
import javax.persistence.QueryHint;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity(name = "EntityMutex")
@Table(name = "MUTEX")
@EntityListeners(EntityAuditListener.class)
@NamedQuery(name = "mutex.findOneForUpdate", query = "SELECT m FROM EntityMutex m WHERE m.resourceCd = :resourceCd",
        lockMode = LockModeType.PESSIMISTIC_WRITE, hints = {@QueryHint(name = "javax.persistence.query.timeout", value = "1000")})
@Getter
@Setter
public class EntityMutex implements IEntityAudit, IEntityVersion, Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "RESOURCE_CD")
    private String resourceCd;

    @NotNull
    @Column(name = "NODE_ID")
    private String nodeId;

    @NotNull
    @Column(name = "LOCK_UNTIL")
    private Timestamp lockUntil;

    @NotNull
    @Column(name = "CREATED_BY")
    private String createdBy;

    @NotNull
    @Column(name = "CREATED_DATE")
    private Timestamp createdDateTs;

    @NotNull
    @Column(name = "LAST_UPDATED_BY")
    private String lastUpdatedBy;

    @NotNull
    @Column(name = "LAST_UPDATED_DATE")
    private Timestamp lastUpdatedDateTs;

    @Version
    @Column(name = "VERSION")
    private Integer version;
}
