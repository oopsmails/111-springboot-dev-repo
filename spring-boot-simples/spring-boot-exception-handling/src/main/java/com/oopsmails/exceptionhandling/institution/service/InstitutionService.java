package com.oopsmails.exceptionhandling.institution.service;

import com.oopsmails.exceptionhandling.institution.domain.InstitutionDto;
import com.oopsmails.exceptionhandling.institution.entity.Institution;
import com.oopsmails.exceptionhandling.institution.repo.jpa.InstitutionJpaRepository;
import lombok.SneakyThrows;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstitutionService {
    @Autowired
    private InstitutionJpaRepository institutionJpaRepository;

    public List<Institution> retrieveEntityInstitutionAll() {
        return institutionJpaRepository.findAll();
    }

    public Institution saveEntityInstitutionWithBranches(Institution institution) {
        return institutionJpaRepository.save(institution);
    }

    @SneakyThrows
    public InstitutionDto retrieveInstitutionById(Long id) {
        Institution institution = institutionJpaRepository.findById(id).orElse(null);

        InstitutionDto result = new InstitutionDto();

        BeanUtils.copyProperties(result, institution);
        return result;
    }
}
