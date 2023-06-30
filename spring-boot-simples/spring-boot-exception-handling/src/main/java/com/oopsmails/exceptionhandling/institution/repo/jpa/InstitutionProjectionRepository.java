package com.oopsmails.exceptionhandling.institution.repo.jpa;

import com.oopsmails.exceptionhandling.institution.entity.Institution;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InstitutionProjectionRepository extends JpaRepository<Institution, Long> {

    // Query to return branch IDs with Institution columns for all institutions
    @Query("SELECT i.id AS institutionId, " +
            "b.id AS branchId, " +
            "i.institutionName AS institutionName, " +
            "i.description AS description " +
//            "FROM Institution i JOIN i.branchList b")
            "FROM Institution i LEFT JOIN i.branchList b ")
    List<BranchIdProjection> findAllBranchIdsWithInstitution();

    // Query to return full branches with Institution columns for a specific institution
    @Query("SELECT i.id AS institutionId, " +
            "b.id AS branchId, " +
            "b.branchName AS branchName, " +
            "i.institutionName AS institutionName, " +
            "i.description AS description " +
            "FROM Institution i JOIN i.branchList b WHERE i.id = :institutionId")
    List<FullBranchProjection> findFullBranchesByInstitutionIdWithInstitution(@Param("institutionId") Long institutionId);

    // Projection for branch IDs with Institution columns
    interface BranchIdProjection {
        Long getInstitutionId();
        Long getBranchId();
        String getInstitutionName();
        String getDescription();
        // Include other Institution properties you need
    }

    // Projection for full branches with Institution columns
    interface FullBranchProjection {
        Long getInstitutionId();
        Long getBranchId();

        String getBranchName();

        String getInstitutionName();

        String getDescription();
        // Include other properties you need
    }
}