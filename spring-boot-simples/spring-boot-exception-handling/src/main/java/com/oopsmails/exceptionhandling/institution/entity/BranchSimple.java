package com.oopsmails.exceptionhandling.institution.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "BRANCH")
@Entity
@Data
public class BranchSimple {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BRANCH_ID")
    private Long branchId;

    @Column(name = "INSTITUTION_ID")
    private Long institutionId;

    @Column(name = "BRANCH_NAME")
    private String branchName;

    @Column(name = "DESCRIPTION")
    private String description;

}
