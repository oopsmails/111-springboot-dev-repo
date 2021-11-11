package com.oopsmails.exceptionhandling.mutex.service;

import com.oopsmails.common.annotation.model.logging.LoggingOrigin;
import com.oopsmails.common.annotation.performance.LoggingPerformance;
import com.oopsmails.exceptionhandling.mutex.adapter.MutexAdapter;
import com.oopsmails.exceptionhandling.mutex.dto.Mutex;
import com.oopsmails.exceptionhandling.mutex.entity.EntityMutex;
import com.oopsmails.exceptionhandling.mutex.repository.IMutexRepository;
import com.oopsmails.exceptionhandling.util.DbCommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PessimisticLockException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class MutexServiceImpl implements IMutexService {
    @Autowired
    private IMutexRepository mutexRepository;

    @Autowired
    private IAuditService auditService;

    @Autowired
    private DbCommonService dbCommonService;

    @PersistenceContext
    private EntityManager entityManager;

    private String hostName;

    public MutexServiceImpl() throws UnknownHostException {
        this.hostName = InetAddress.getLocalHost().getHostName();
        log.info("Host name: [{}]", this.hostName);
    }

    @Override
    @LoggingPerformance(origin = LoggingOrigin.Service, transaction = "MutexService.lock")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public boolean lock(String resourceCd, Duration duration) {
        log.info("Start to find Mutex for update and acquire it, for resourceCode: [{}]", resourceCd);
        try {
            Mutex mutex = null;
            List<?> list = this.entityManager.createNamedQuery("mutex.findOneForUpdate").setParameter("resourceCd", resourceCd).getResultList();
            if (list != null && !list.isEmpty()) {
                mutex = MutexAdapter.toMutex((EntityMutex) list.get(0));
                LocalDateTime dbCurrentTimestamp = dbCommonService.getCurrentTimestamp();
                log.debug("In DB, entity mutex resourceCd: {}, mutex nodeId: {}, DB current timestamp: {}", mutex.getResourceCode(), mutex.getNodeId(), dbCurrentTimestamp);

                if ((!mutex.getNodeId().equals(this.hostName)) && mutex.getLockUntil().isAfter(dbCurrentTimestamp)) {
                    log.info("Cannot acquire mutex [{}] for hostName [{}] because it is already acquired by [{}]", resourceCd, this.hostName, mutex.getNodeId());
                    return false;
                }
            }

            log.debug("Trying to acquire the lock, if not found in db, then insert, thus, need to define all fields here ....");
            if (mutex == null) {
                mutex = new Mutex();
                mutex.setResourceCode(resourceCd);
            }

            mutex.setNodeId(this.hostName);
            mutex.setLockUntil(this.getLockUntilTime(duration));
            mutex = this.save(mutex);
            log.info("mutex acquired successfully for resourceCd [{}] on nodeId [{}] until [{}] version [{}]", mutex.getResourceCode(), mutex.getNodeId(), mutex.getLockUntil(), mutex.getVersion());
        } catch (PessimisticLockException lockException) {
            /**
             * Failed on SELECT because an existing lock is there by another connection.
             *
             * If using JPA then the exception is ObjectOptimisticLockingFailureException.
             * Here, JPA is NOT used for select because it is hard to control the timeout
             */
            log.warn("Failed to SELECT mutex [{}] for host [{}] because it is already locked in the databased by a different connection, get PessimisticLockException: {}",
                    resourceCd, this.hostName, lockException.getMessage());
            // should not throw original exception
            throw new OopsPessimisticLockException("Failed to SELECT mutex ["
                    + resourceCd
                    + "] for host [" + this.hostName
                    + "] because it is already locked in the databased by a different connection, get PessimisticLockException: {}");
        }

        // mutex acquired
        return true;
    }

    @Override
    @Transactional
    public boolean release(String resourceCd) {
        log.info("Start to find Mutex for update and acquire it, for resourceCode: [{}]", resourceCd);
        try {
            Mutex mutex = null;
            List<?> list = this.entityManager.createNamedQuery("mutex.findOneForUpdate").setParameter("resourceCd", resourceCd).getResultList();
            if (list == null || list.isEmpty()) {
                // should NEVER happen
                throw new OopsPessimisticLockException("Failed to SELECT mutex ["
                        + resourceCd
                        + "] for host [" + this.hostName
                        + "] because it is already locked in the databased by a different connection, get PessimisticLockException: {}");
            }

            mutex = MutexAdapter.toMutex((EntityMutex) list.get(0));
            LocalDateTime dbCurrentTimestamp = dbCommonService.getCurrentTimestamp();
            log.debug("In DB, entity mutex resourceCd: {}, mutex nodeId: {}, DB current timestamp: {}", mutex.getResourceCode(), mutex.getNodeId(), dbCurrentTimestamp);

            if ((!mutex.getNodeId().equals(this.hostName))) {
                log.info("Cannot release mutex [{}] for hostName [{}] because it is currently holding by [{}]", resourceCd, this.hostName, mutex.getNodeId());
                return false;
            }
            if (mutex.getLockUntil().isBefore(dbCurrentTimestamp)) {
                log.info("Cannot release mutex [{}] for hostName [{}] because it is past expiration time [{}]", resourceCd, this.hostName, mutex.getLockUntil());
                return false;
            }

            log.debug("Trying to release the lock ....");
            mutex.setLockUntil(LocalDateTime.now());
            mutex = this.save(mutex);
            log.info("mutex released successfully for resourceCd [{}] on nodeId [{}] until [{}] version [{}]", mutex.getResourceCode(), mutex.getNodeId(), mutex.getLockUntil(), mutex.getVersion());
        } catch (PessimisticLockException pessimisticLockException) {
            /**
             * Failed on SELECT because an existing lock is there by another connection.
             *
             * If using JPA then the exception is ObjectOptimisticLockingFailureException.
             * Here, JPA is NOT used for select because it is hard to control the timeout
             */
            log.warn("Failed to SELECT mutex [{}] for host [{}] because it is already locked in the databased by a different connection, get PessimisticLockException: {}",
                    resourceCd, this.hostName, pessimisticLockException.getMessage());
            // should not throw original exception
            throw new OopsPessimisticLockException("Failed to SELECT mutex ["
                    + resourceCd
                    + "] for host [" + this.hostName
                    + "] because it is already locked in the databased by a different connection, get PessimisticLockException: {}");
        }

        // mutex released
        return true;
    }

    @Override
    @LoggingPerformance(origin = LoggingOrigin.Service, transaction = "MutexService.save")
    public Mutex save(Mutex mutex) {
        EntityMutex entityMutex = MutexAdapter.toEntityMutex(mutex);

        try {
            this.auditService.audit(entityMutex);
            entityMutex = this.mutexRepository.saveAndFlush(entityMutex);
            Mutex saved = MutexAdapter.toMutex(entityMutex);
            return saved;
        } catch (ObjectOptimisticLockingFailureException optimisticLockingFailureException) {
            // Failed on UPDATE, should never be here because we use pessimistic locking
            throw new OopsOptimisticLockException("Failed to UPDATE mutex ["
                    + mutex.getResourceCode()
                    + "] for host [" + this.hostName
                    + "] because it is already locked in the databased by a different connection, get PessimisticLockException: {}");
        } catch (DataIntegrityViolationException violationException) {
            // Failed on INSERT
            log.warn("Failed to INSERT mutex [{}] for host [{}] because it already exists in db, get DataIntegrityViolationException: {}",
                    mutex.getResourceCode(), this.hostName, violationException.getMessage());
            throw new OopsOptimisticLockException("Failed to INSERT mutex ["
                    + mutex.getResourceCode()
                    + "] for host [" + this.hostName
                    + "] because it already exists in db, get DataIntegrityViolationException: {}");
        }
    }

    @Override
    @LoggingPerformance(origin = LoggingOrigin.Service, transaction = "MutexService.findByResourceCode")
    public Mutex findByResourceCode(String resourceCode) {
        Optional<EntityMutex> opt = this.mutexRepository.findById(resourceCode);
        if (!opt.isPresent()) {
            return null;
        }
        Mutex mutex = MutexAdapter.toMutex(opt.get());
        return mutex;
    }

    @Override
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    private LocalDateTime getLockUntilTime(Duration duration) {
        LocalDateTime result = dbCommonService.getCurrentTimestamp();
        return result.plusNanos(duration.toNanos());
    }
}
