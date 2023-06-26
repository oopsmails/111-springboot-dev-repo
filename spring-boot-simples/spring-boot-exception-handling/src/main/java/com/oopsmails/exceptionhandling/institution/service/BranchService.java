package com.oopsmails.exceptionhandling.institution.service;

import com.oopsmails.exceptionhandling.institution.domain.BranchDto;
import com.oopsmails.exceptionhandling.institution.entity.BranchSimple;
import com.oopsmails.exceptionhandling.institution.repo.jpa.BranchJpaRepository;
import com.oopsmails.exceptionhandling.institution.repo.jpa.BranchSimpleJpaRepository;
import lombok.SneakyThrows;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BranchService {

    @Autowired
    private BranchJpaRepository branchJpaRepository;

    @Autowired
    private BranchSimpleJpaRepository branchSimpleJpaRepository;

    @SneakyThrows
    public BranchDto retrieveBranch(Long id) {
        BranchSimple branchSimple = branchSimpleJpaRepository.findById(id).orElse(null);

        BranchDto result = new BranchDto();

        BeanUtils.copyProperties(result, branchSimple);
        return result;
    }
}
