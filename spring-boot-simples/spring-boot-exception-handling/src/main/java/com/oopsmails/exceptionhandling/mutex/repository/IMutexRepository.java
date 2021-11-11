package com.oopsmails.exceptionhandling.mutex.repository;

import com.oopsmails.exceptionhandling.mutex.entity.EntityMutex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;

@Repository
public interface IMutexRepository extends JpaRepository<EntityMutex, String> {

    /**
     * This is NOT actually in use because it it not easy to set pessimistic timeout on a single query
     *
     * See @NamedQuery(name = "mutex.findOneForUpdate" ...) in EntityMutex
     *
     * @param resourceCd
     * @return
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT m FROM EntityMutex m WHERE m.resourceCd = :resourceCd")
    EntityMutex findOneForUpdate(@Param("resourceCd") String resourceCd);
}
