package com.oopsmails.exceptionhandling.institution.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "INSTITUTION")
@Entity
@Data
public class InstitutionSimple {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INSTITUTION_ID")
    private Long institutionId;

    @Column(name = "INSTITUTION_NAME")
    private String institutionName;

    @Column(name = "DESCRIPTION")
    private String description;

}
