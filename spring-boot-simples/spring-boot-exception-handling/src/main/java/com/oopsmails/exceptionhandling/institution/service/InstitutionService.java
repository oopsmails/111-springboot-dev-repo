package com.oopsmails.exceptionhandling.institution.service;

import com.oopsmails.exceptionhandling.institution.entity.Institution;
import com.oopsmails.exceptionhandling.institution.repo.jpa.InstitutionJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstitutionService {

//    private final InstitutionRepository institutionRepository;
//
//    @Autowired
//    public InstitutionService(InstitutionRepository institutionRepository) {
//        this.institutionRepository = institutionRepository;
//    }

    @Autowired
    private InstitutionJpaRepository institutionJpaRepository;

//    public InstitutionDto saveInstitutionWithBranches(InstitutionDto institution) {
//        return institutionRepository.save(institution);
//    }

    public List<Institution> retrieveEntityInstitutionAll() {
        return institutionJpaRepository.findAll();
    }

    public Institution saveEntityInstitutionWithBranches(Institution institution) {
        return institutionJpaRepository.save(institution);
    }
}
