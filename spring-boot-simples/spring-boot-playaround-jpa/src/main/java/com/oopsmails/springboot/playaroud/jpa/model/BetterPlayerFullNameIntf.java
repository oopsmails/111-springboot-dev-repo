package com.oopsmails.springboot.playaroud.jpa.model;

public interface BetterPlayerFullNameIntf {

    String getFirstName();

    String getLastName();

    default String getFullName() {
        return getLastName() + ", " + getFirstName();
    }
}
