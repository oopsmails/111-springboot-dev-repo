package com.oopsmails.exceptionhandling.institution.controller;

import com.oopsmails.exceptionhandling.institution.entity.Branch;
import com.oopsmails.exceptionhandling.institution.entity.Institution;
import com.oopsmails.exceptionhandling.institution.repo.jpa.BranchJpaRepository;
import com.oopsmails.exceptionhandling.institution.service.InstitutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class InstitutionController {

    private final InstitutionService institutionService;
    @Autowired
    private BranchJpaRepository branchJpaRepository;

    @Autowired
    public InstitutionController(InstitutionService institutionService) {
        this.institutionService = institutionService;
    }

    @GetMapping("/institutions")
    public List<Institution> retrieveAll() {
        return institutionService.retrieveEntityInstitutionAll();
    }

    @PostMapping("/institution")
    public Institution createInstitutionEntity(@RequestBody Institution institution) {
        return institutionService.saveEntityInstitutionWithBranches(institution);
    }

    @GetMapping("/branches")
    public List<Branch> retrieveAllBranches() {
        return branchJpaRepository.findAll();
    }

    @GetMapping("/branch/{id}")
    public Branch retrieveBranchById(@PathVariable Long id) {
        return branchJpaRepository.findById(id).orElse(null);
    }
}