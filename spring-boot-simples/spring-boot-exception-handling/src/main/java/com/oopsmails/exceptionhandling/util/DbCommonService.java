package com.oopsmails.exceptionhandling.util;

import com.oopsmails.exceptionhandling.mutex.entity.InstantItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.TimeZone;

@Service
@Slf4j
public class DbCommonService {
    @Value("${app.sql.sellect.current.timestamp}")
    private String queryCurrentTimestamp;

    @PersistenceContext
    private EntityManager entityManager;

    public LocalDateTime getCurrentTimestamp() {
        Query query = this.entityManager.createNativeQuery(queryCurrentTimestamp, InstantItem.class);
//        Timestamp timestamp = (Timestamp) query.getSingleResult();
//        LocalDateTime now = timestamp.toLocalDateTime();

        InstantItem instantItem = (InstantItem) query.getSingleResult();
        LocalDateTime now = LocalDateTime.ofInstant(instantItem.getDate(), TimeZone.getDefault().toZoneId());
        log.debug("DB current timestamp: {}", now);

        return now;
    }
}
