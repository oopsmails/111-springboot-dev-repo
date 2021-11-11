package com.oopsmails.exceptionhandling.mutex.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.Instant;

/**
 * This class is used to handle select timestamp directly from db.
 * Without this wrapper, will get error, "org.hibernate.MappingException: No Dialect mapping for JDBC type: 2014"
 */

@Entity
public class InstantItem {
    private Instant date;

    /**
     * @return the date
     */
    @Id
    @Column(name = "DATE_VALUE")
    public Instant getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Instant date) {
        this.date = date;
    }
}
