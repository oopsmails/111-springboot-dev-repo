package com.oopsmails.generaljava.highconcurrency;

import lombok.Data;

import java.util.Random;

@Data
public class Person {
    static Random random = new Random();
    String name;

    public Person(String name) {
        this.name = name;
    }

    public void arrive() {
        MarriagePhaser.milliSleep(random.nextInt(1000));
        System.out.printf("%s Get to the scene!\n", name);
    }

    public void eat() {
        MarriagePhaser.milliSleep(random.nextInt(1000));
        System.out.printf("%s Finish eating!\n", name);
    }

    public void leave() {
        MarriagePhaser.milliSleep(random.nextInt(1000));
        System.out.printf("%s Leave!\n", name);
    }
}
