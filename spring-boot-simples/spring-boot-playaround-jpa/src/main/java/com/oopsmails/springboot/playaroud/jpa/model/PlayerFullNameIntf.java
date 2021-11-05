package com.oopsmails.springboot.playaroud.jpa.model;

import org.springframework.beans.factory.annotation.Value;

public interface PlayerFullNameIntf {

    @Value("#{target.lastName +', ' + target.firstName}")
    String getFullName();
}
