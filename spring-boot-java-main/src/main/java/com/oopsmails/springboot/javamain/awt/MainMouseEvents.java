package com.oopsmails.springboot.javamain.awt;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Robot;

public class MainMouseEvents {
    public static void main(String[] args) {
        try {
            while (true) {
                slightlyMouseMove(30000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void slightlyMouseMove(int millis) throws AWTException, InterruptedException {
        Robot robot = new Robot();
        robot.delay(millis);
        robot.mouseMove(MouseInfo.getPointerInfo().getLocation().x + 1, MouseInfo.getPointerInfo().getLocation().y + 1);
        robot.mouseMove(MouseInfo.getPointerInfo().getLocation().x - 1, MouseInfo.getPointerInfo().getLocation().y - 1);
    }

}
