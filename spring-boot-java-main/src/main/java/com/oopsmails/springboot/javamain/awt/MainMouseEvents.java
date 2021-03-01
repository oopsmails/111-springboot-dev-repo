package com.oopsmails.springboot.javamain.awt;

import java.awt.*;
import java.awt.event.MouseEvent;

public class MainMouseEvents {
    private static Robot robot = null;

    public static void main(String[] args) {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        while (true) {
            simulateRightClick(1, 1100, 30000); // in ms
        }

    }

    public static void simulateRightClick(int x, int y, int delay) {
        // robot.mouseMove(x, y);
        robot.delay(delay);
        robot.mousePress(MouseEvent.BUTTON3_DOWN_MASK);
        robot.mouseRelease(MouseEvent.BUTTON3_DOWN_MASK);
    }
}
