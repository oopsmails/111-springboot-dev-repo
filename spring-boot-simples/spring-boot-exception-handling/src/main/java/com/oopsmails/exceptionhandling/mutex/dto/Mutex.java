package com.oopsmails.exceptionhandling.mutex.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Mutex extends AbstractAuditAndVersion {
    private static final long serialVersionUID = 1L;

    private String resourceCode;

    private String nodeId;

    private LocalDateTime lockUntil;

    private Integer version;
}
