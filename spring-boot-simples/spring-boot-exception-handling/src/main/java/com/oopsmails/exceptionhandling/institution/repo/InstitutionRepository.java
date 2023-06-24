package com.oopsmails.exceptionhandling.institution.repo;

import com.oopsmails.exceptionhandling.institution.domain.Institution;
import com.oopsmails.exceptionhandling.institution.entity.InstitutionEntity;
import com.oopsmails.exceptionhandling.institution.repo.jpa.InstitutionJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class InstitutionRepository {
    @Autowired
    private InstitutionJpaRepository institutionJpaRepository;

    public InstitutionEntity save(InstitutionEntity institutionEntity) {
        return institutionJpaRepository.save(institutionEntity);
    }
}
