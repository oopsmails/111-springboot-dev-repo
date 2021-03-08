package com.oopsmails.springboot.javamain.awt;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.event.MouseEvent;
import java.util.Random;

public class MainMouseEventsExamples {
    public static void main(String[] args) {
        try {
            while (true) {

//                simulateRightClick(1, 1100, 30000); // in ms
//                printMousePosition(30000);

                slightlyMouseMove(30000);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void simulateRightClick(int x, int y, int delay) throws AWTException {
        Robot robot = new Robot();

        // robot.mouseMove(x, y);
        robot.delay(delay);
        robot.mousePress(MouseEvent.BUTTON3_DOWN_MASK);
//        robot.mouseRelease(MouseEvent.BUTTON3_DOWN_MASK);
    }

    public static void printMousePosition(long millis) throws InterruptedException {
        Thread.sleep(millis);
        System.out.println("(" + MouseInfo.getPointerInfo().getLocation().x + ", " + MouseInfo.getPointerInfo().getLocation().y + ")");
    }

    public static void randomMouseMove(int x, int y, int delay) throws AWTException, InterruptedException {
        final int FIVE_SECONDS = 5000;
        final int MAX_Y = 400;
        final int MAX_X = 400;

        Robot robot = new Robot();
        Random random = new Random();
        while (true) {
            robot.mouseMove(random.nextInt(MAX_X), random.nextInt(MAX_Y));
            Thread.sleep(FIVE_SECONDS);
        }
    }

    public static void slightlyMouseMove(long millis) throws AWTException, InterruptedException {
        Thread.sleep(millis);

        Robot robot = new Robot();
        robot.mouseMove(MouseInfo.getPointerInfo().getLocation().x + 1,MouseInfo.getPointerInfo().getLocation().y + 1);
        robot.mouseMove(MouseInfo.getPointerInfo().getLocation().x - 1,MouseInfo.getPointerInfo().getLocation().y - 1);
    }

}

