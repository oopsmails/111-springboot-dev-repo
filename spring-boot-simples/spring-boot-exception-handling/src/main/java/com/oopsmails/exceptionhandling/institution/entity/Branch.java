package com.oopsmails.exceptionhandling.institution.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name = "BRANCH")
@Entity
@Data
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BRANCH_ID")
    private Long branchId;

    @Column(name = "BRANCH_NAME")
    private String branchName;

    @Column(name = "DESCRIPTION")
    private String description;

    //    @ManyToOne
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "institution_id", nullable = false, insertable = false, updatable = false)
    @JsonBackReference
    private Institution institution;
}
