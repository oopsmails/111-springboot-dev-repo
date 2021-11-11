package com.oopsmails.exceptionhandling.mutex.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.Instant;

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
