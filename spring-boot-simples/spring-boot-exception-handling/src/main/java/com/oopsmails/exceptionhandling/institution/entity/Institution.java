package com.oopsmails.exceptionhandling.institution.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Table(name = "INSTITUTION")
@Entity
@Data
//@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@institutionId")
public class Institution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INSTITUTION_ID")
    private Long institutionId;

    @Column(name = "INSTITUTION_NAME")
    private String institutionName;

    @Column(name = "DESCRIPTION")
    private String description;

    //    @OneToMany(targetEntity = Branch.class,cascade=CascadeType.ALL)
    //    @OneToMany(targetEntity = Branch.class,cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    @OneToMany(targetEntity = Branch.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "institution_id", nullable = false)
    @JsonManagedReference
//    @JsonBackReference
//        @JsonIgnore // can also use this for stackOverflow error
    private List<Branch> branchList;

    public void addBranchEntity(Branch branch) {
        branch.setInstitution(this);
        this.branchList.add(branch);
    }

    public void removeBranchEntity(Branch branch) {
        branch.setInstitution(null);
        this.branchList.remove(branch);
    }

}
