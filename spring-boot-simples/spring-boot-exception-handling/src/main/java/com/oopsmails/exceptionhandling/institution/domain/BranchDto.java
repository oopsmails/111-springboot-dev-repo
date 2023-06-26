package com.oopsmails.exceptionhandling.institution.domain;

import lombok.Data;

@Data
public class BranchDto {
    private Long branchId;
    private Long institutionId;
    private String branchName;
    private String description;
}
