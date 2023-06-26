package com.oopsmails.exceptionhandling.institution.controller;

import com.oopsmails.exceptionhandling.institution.domain.BranchDto;
import com.oopsmails.exceptionhandling.institution.domain.InstitutionDto;
import com.oopsmails.exceptionhandling.institution.entity.Branch;
import com.oopsmails.exceptionhandling.institution.entity.Institution;
import com.oopsmails.exceptionhandling.institution.repo.jpa.BranchJpaRepository;
import com.oopsmails.exceptionhandling.institution.repo.jpa.BranchSimpleJpaRepository;
import com.oopsmails.exceptionhandling.institution.service.BranchService;
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

    @Autowired
    private InstitutionService institutionService;

    @Autowired
    private BranchService branchService;

    @Autowired
    private BranchJpaRepository branchJpaRepository;

    @Autowired
    private BranchSimpleJpaRepository branchSimpleJpaRepository;

    @GetMapping("/institutions")
    public List<Institution> retrieveAll() {
        return institutionService.retrieveEntityInstitutionAll();
    }

    @GetMapping("/institution/{id}")
    public InstitutionDto retrieveInstitutionById(@PathVariable Long id) {
        return institutionService.retrieveInstitutionById(id);
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
    //    public BranchDto retrieveBranchById(@PathVariable Long id, @RequestParam(name = "simple", required = false) boolean isSimple) {
    public BranchDto retrieveBranchById(@PathVariable Long id) {
        return branchService.retrieveBranch(id);
    }
}