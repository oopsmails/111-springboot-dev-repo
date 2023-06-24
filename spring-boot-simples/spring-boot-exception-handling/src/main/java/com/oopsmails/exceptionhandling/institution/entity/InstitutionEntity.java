package com.oopsmails.exceptionhandling.institution.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class InstitutionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INSTITUTION_ID")
    private Long institutionId;

    @Column(name = "INSTITUTION_NAME")
    private String institutionName;

    @Column(name = "DESCRIPTION")
    private String description;

    @OneToMany(mappedBy = "institutionEntity", cascade = CascadeType.ALL)
    private List<BranchEntity> branchEntityList;
}
