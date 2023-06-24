package com.oopsmails.exceptionhandling.institution.service;

import com.oopsmails.exceptionhandling.institution.domain.Institution;
import com.oopsmails.exceptionhandling.institution.entity.InstitutionEntity;
import com.oopsmails.exceptionhandling.institution.repo.InstitutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InstitutionService {

    private final InstitutionRepository institutionRepository;

    @Autowired
    public InstitutionService(InstitutionRepository institutionRepository) {
        this.institutionRepository = institutionRepository;
    }

    public Institution saveInstitutionWithBranches(Institution institution) {
//        return institutionRepository.save(institution);
        return null;
    }

    public InstitutionEntity saveEntityInstitutionWithBranches(InstitutionEntity institutionEntity) {
        return institutionRepository.save(institutionEntity);
    }
}
