package com.oopsmails.exceptionhandling.institution.repo.jpa;

import com.oopsmails.exceptionhandling.institution.entity.Branch;
import com.oopsmails.exceptionhandling.institution.entity.Institution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchJpaRepository extends JpaRepository<Branch, Long> {
}