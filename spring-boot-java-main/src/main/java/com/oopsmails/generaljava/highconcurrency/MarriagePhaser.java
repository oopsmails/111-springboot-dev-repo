package com.oopsmails.generaljava.highconcurrency;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

@Data
@Slf4j
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
                log.info("======> phase {}: Everybody's here!", phase);
                return false;
            case 1:
                log.info("======> phase {}: Everyone's finished!", phase);
                return false;
            case 2:
                log.info("======> phase {}: Everybody's gone!", phase);
                log.info("======> phase {}: The wedding is over!", phase);
                return true;
            default:
                return true;
        }
    }
}
