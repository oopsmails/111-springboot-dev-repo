package com.oopsmails.exceptionhandling.institution.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.oopsmails.exceptionhandling.institution.entity.Institution;
import lombok.Data;

@Data
//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "branchId")
public class BranchDto {
    private Long branchId;
    private Long institutionId;
    private String branchName;
    private String description;
    private Institution institution;
}
