package com.oopsmails.exceptionhandling.institution.repo.jpa;

import com.oopsmails.exceptionhandling.institution.entity.InstitutionSimple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstitutionSimpleJpaRepository extends JpaRepository<InstitutionSimple, Long> {
}