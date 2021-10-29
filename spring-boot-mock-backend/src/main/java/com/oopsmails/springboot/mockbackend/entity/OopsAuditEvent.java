package com.oopsmails.springboot.mockbackend.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "AUDIT_EVENT")
@Data
public class OopsAuditEvent {
    @Id
    @Column(name = "EVENT_ID")
    private String eventId;

    @Column(name = "EVENT_SOURCE")
    private String eventSource;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Lob
    @Column(name = "EVENT_JSON")
    private String eventJson;

    @Column(name = "CREATED_DATE")
    private Timestamp createdDate;
}
