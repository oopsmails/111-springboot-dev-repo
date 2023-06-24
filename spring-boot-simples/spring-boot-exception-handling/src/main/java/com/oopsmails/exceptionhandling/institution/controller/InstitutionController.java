package com.oopsmails.exceptionhandling.institution.controller;

import com.oopsmails.exceptionhandling.institution.entity.InstitutionEntity;
import com.oopsmails.exceptionhandling.institution.service.InstitutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InstitutionController {

    private final InstitutionService institutionService;

    @Autowired
    public InstitutionController(InstitutionService institutionService) {
        this.institutionService = institutionService;
    }

    @PostMapping("/institutions")
    public InstitutionEntity createInstitutionEntity(@RequestBody InstitutionEntity institutionEntity) {
        return institutionService.saveEntityInstitutionWithBranches(institutionEntity);
    }
}