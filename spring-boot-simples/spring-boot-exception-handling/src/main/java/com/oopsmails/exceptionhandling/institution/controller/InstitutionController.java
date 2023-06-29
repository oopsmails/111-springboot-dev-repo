package com.oopsmails.exceptionhandling.institution.controller;

import com.oopsmails.exceptionhandling.institution.domain.BranchDto;
import com.oopsmails.exceptionhandling.institution.domain.InstitutionDto;
import com.oopsmails.exceptionhandling.institution.entity.Branch;
import com.oopsmails.exceptionhandling.institution.entity.Institution;
import com.oopsmails.exceptionhandling.institution.repo.jpa.BranchJpaRepository;
import com.oopsmails.exceptionhandling.institution.repo.jpa.BranchSimpleJpaRepository;
import com.oopsmails.exceptionhandling.institution.repo.jpa.InstitutionProjectionRepository;
import com.oopsmails.exceptionhandling.institution.service.BranchService;
import com.oopsmails.exceptionhandling.institution.service.InstitutionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class InstitutionController {

    @Autowired
    private InstitutionService institutionService;

    @Autowired
    private InstitutionProjectionRepository institutionProjectionRepository;

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

    @GetMapping("/institutions-v2")
    public List<InstitutionProjectionRepository.BranchIdProjection> retrieveAllV2() {
        List<InstitutionProjectionRepository.BranchIdProjection> result = institutionProjectionRepository.findAllBranchIdsWithInstitution();
        result.forEach((item) -> {
            log.info(item.getInstitutionName() + " -- " + item.getBranchId());
        });
        return result;
    }

    @GetMapping("/institution-v2/{id}")
    public List<InstitutionProjectionRepository.FullBranchProjection> retrieveInstitutionByIdV2(@PathVariable Long id) {
        List<InstitutionProjectionRepository.FullBranchProjection> result = institutionProjectionRepository.findFullBranchesByInstitutionIdWithInstitution(id);
        result.forEach((item) -> {
            log.info(item.getInstitutionName() + " -- " + item.getBranchId() + " -- " + item.getBranchName());
        });
        return result;
    }

    @PostMapping("/institution")
    public Institution createInstitutionEntity(@RequestBody Institution institution) {
        return institutionService.saveEntityInstitutionWithBranches(institution);
    }

    @GetMapping("/branches")
    public List<Branch> retrieveAllBranches() {
        return branchJpaRepository.findAll();
    }

    @GetMapping("/branchsimple/{id}")
    //    public BranchDto retrieveBranchById(@PathVariable Long id, @RequestParam(name = "simple", required = false) boolean isSimple) {
    public BranchDto retrieveBranchSimpleById(@PathVariable Long id) {
        return branchService.retrieveBranchSimple(id);
    }

    @GetMapping("/branch-v1/{id}")
    public BranchDto retrieveBranchByIdV1(@PathVariable Long id) {
        return branchService.retrieveBranch(id);
    }

    @GetMapping("/branch-v2/{id}")
    public Branch retrieveBranchByIdV2(@PathVariable Long id) {
        Branch branch = branchJpaRepository.findById(id).orElse(null);
        Institution institution = branch.getInstitution();
        log.info("institutionId = [{}]", institution.getInstitutionId());
        return branch;
    }
}