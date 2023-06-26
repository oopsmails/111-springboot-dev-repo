package com.oopsmails.exceptionhandling.institution.domain;

import com.oopsmails.exceptionhandling.institution.entity.Branch;
import lombok.Data;

import java.util.List;

@Data
public class InstitutionDto {
    private Long institutionId;
    private String institutionName;
    private String description;
    private List<Branch> branchList;

}
