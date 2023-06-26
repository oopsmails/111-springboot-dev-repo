package com.oopsmails.exceptionhandling.institution.repo.jpa;

import com.oopsmails.exceptionhandling.institution.entity.BranchSimple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchSimpleJpaRepository extends JpaRepository<BranchSimple, Long> {
}