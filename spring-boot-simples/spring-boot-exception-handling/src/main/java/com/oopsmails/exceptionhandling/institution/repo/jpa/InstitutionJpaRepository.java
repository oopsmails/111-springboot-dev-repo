package com.oopsmails.exceptionhandling.institution.repo.jpa;

import com.oopsmails.exceptionhandling.institution.entity.Institution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstitutionJpaRepository extends JpaRepository<Institution, Long> {

    // Custom query to fetch all institutions along with the count of branches
    @Query("SELECT i, (SELECT COUNT(b) FROM i.branchList b) AS numOfBranches FROM Institution i")
    List<Object[]> findAllWithNumOfBranches();
}