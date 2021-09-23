package com.oopsmails.generaljava.highconcurrency;

public class TestPhaser {
    static MarriagePhaser phaser = new MarriagePhaser();

    public static void main(String[] args) {

        phaser.bulkRegister(5);

        for (int i = 0; i < 5; i++) {
            final int nameIndex = i;
            new Thread(() -> {

                Person p = new Person("person " + nameIndex);
                p.arrive();
                phaser.arriveAndAwaitAdvance();

                p.eat();
                phaser.arriveAndAwaitAdvance();

                p.leave();
                phaser.arriveAndAwaitAdvance();
            }).start();
        }
    }
}
