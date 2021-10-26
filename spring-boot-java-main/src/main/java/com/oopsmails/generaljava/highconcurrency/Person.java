package com.oopsmails.generaljava.highconcurrency;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Data
@Slf4j
public class Person {
    static Random random = new Random();
    String name;

    public Person(String name) {
        this.name = name;
    }

    public void arrive() {
        MarriagePhaser.milliSleep(random.nextInt(1000));
        log.info("Get to the scene! [{}]", name);
    }

    public void eat() {
        MarriagePhaser.milliSleep(random.nextInt(1000));
        log.info("Finish eating! [{}]", name);
    }

    public void leave() {
        MarriagePhaser.milliSleep(random.nextInt(1000));
        log.info("Leave! [{}]", name);
    }
}
