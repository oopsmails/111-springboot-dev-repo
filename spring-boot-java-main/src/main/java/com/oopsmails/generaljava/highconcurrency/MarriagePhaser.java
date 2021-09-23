package com.oopsmails.generaljava.highconcurrency;

import lombok.Data;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

@Data
public class MarriagePhaser extends Phaser {
    static void milliSleep(int milli) {
        try {
            TimeUnit.MILLISECONDS.sleep(milli);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected boolean onAdvance(int phase, int registeredParties) {

        switch (phase) {
            case 0:
                System.out.println("Everybody's here!");
                return false;
            case 1:
                System.out.println("Everyone's finished!");
                return false;
            case 2:
                System.out.println("Everybody's gone!");
                System.out.println("The wedding is over!");
                return true;
            default:
                return true;
        }
    }
}
