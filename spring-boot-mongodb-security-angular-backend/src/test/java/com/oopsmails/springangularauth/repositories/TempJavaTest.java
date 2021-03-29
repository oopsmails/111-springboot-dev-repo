package com.oopsmails.springangularauth.repositories;

import lombok.Data;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class TempJavaTest {
    private static final Clock clock = Clock.system(ZoneId.of("Canada/Eastern"));

//    @Disabled
    @Test
    public void testBirthdays() throws Exception {
        ZonedDateTime currentDate = ZonedDateTime.now(clock);

        LocalDate today = LocalDate.now();
        LocalDate birthday = LocalDate.of(1960, Month.MARCH, 11);

        LocalDate nextBDay = birthday.withYear(today.getYear());

        // If your birthday has occurred this year already, add 1 to the year.
        if (nextBDay.isBefore(today) || nextBDay.isEqual(today)) {
            nextBDay = nextBDay.plusYears(1);
        }

        Period p = Period.between(today, nextBDay);
        long p2 = ChronoUnit.DAYS.between(today, nextBDay);
        System.out.println("There are " + p.getMonths() + " months, and " +
                p.getDays() + " days until your next birthday. (" +
                p2 + " total)");
    }


    /**
     * When to use new String(...)
     * <p>
     * https://stackoverflow.com/questions/390703/what-is-the-purpose-of-the-expression-new-string-in-java
     * <p>
     * 1. The one place where you may need new String(String) is to force a substring to copy to a new underlying character array, as in
     * <p>
     * small=new String(huge.substring(10,20))
     * <p>
     * 2. this useful is in declaring lock variables, though Object lock = new Object() is normally used.
     * <p>
     * private final String lock = new String("Database lock");
     * <p>
     * ....
     * <p>
     * synchronized(lock)
     * {
     * // do something
     * }
     */
    @Test
    public void testStringPool() {
        String constantString1 = "Baeldung";
        String constantString2 = "Baeldung";

        org.assertj.core.api.Assertions.assertThat(constantString1)
                .isSameAs(constantString2);

        assertTrue(constantString1 == constantString2);

        System.out.println("==========================================================");

        String newString1 = "HELLO";
        String newString2 = "HELLO";
        String constantString3 = "abc";
        String constantString4 = "abc";

        System.out.println("newString1 == newString2: " + (newString1 == newString2));
        System.out.println("newString1.equals(newString2): " + newString1.equals(newString2));
        System.out.println("constantString3 == constantString4: " + (constantString3 == constantString4));
        assertTrue(constantString3 == constantString4);
        System.out.println("constantString3.equals(constantString4): " + constantString3.equals(constantString4));
    }

    @Test
    public void testStringReverse() {
        String s1 = "123456781234";
        StringBuilder result = new StringBuilder();
        for (int i = s1.length() - 1; i >= 0; i--) {
            result.append((i >= 4 && i <= 7) ? "x" : s1.charAt(i));
        }
        System.out.println(result.toString());
    }

    @Test
    public void testIntFmt() {
        String digitStr = "7654123";
        StringBuilder result = new StringBuilder();
        int count = 0;
        for (int i = digitStr.length() - 1; i >= 0; i--) {
            result.append(digitStr.charAt(i));
            if (count % 3 == 2 && i != 0) {
                result.append(",");
                count = 0;
            } else {
                count++;
            }
        }
        System.out.println("testIntFmt: " + result.reverse().toString());
    }

    @Test
    public void testIntFmtV2() {
        String digitStr = "7654123";
        String result = "";
        int count = 0;
        for (int i = digitStr.length() - 1; i >= 0; i--) {
            char ch = digitStr.charAt(i);
            result = ch + result;
            if (count % 3 == 2 && i != 0) {
                result = "," + result;
                count = 0;
            } else {
                count++;
            }
        }
        System.out.println("testIntFmtV2: " + result);
    }

    @Test
    public void testIntFmtV3() {
        String digitStr = "654123";
        StringBuilder result = new StringBuilder();
        int count = 0;
        for (int i = digitStr.length() - 1; i >= 0; i--) {
            result.insert(0, digitStr.charAt(i));
            if (count % 3 == 2 && i != 0) {
                result.insert(0, ",");
                count = 0;
            } else {
                count++;
            }
        }
        System.out.println("testIntFmt: " + result.toString());
    }

    @Test
    public void testBoadHit() {
        int resultH = 0;

        String boat = "1B 2C,2D 4D";
//         String boat = "2D 4D";
        String[] boats = boat.split(",");

        String hit = "2B 2D 3D 4D 4A";
        String[] hits = hit.split(" ");
        String[] h1 = hits;

        String[] b1 = boats;

        Map<String, Boat> boatMap = new HashMap<>();

        for (String s : b1) {
            String[] b2 = s.split(" ");

            Boat b = new Boat();

            int v1 = Integer.parseInt("" + b2[0].charAt(0));
            int v2 = Integer.parseInt("" + b2[1].charAt(0));
            int ver2 = v2;

            char ho1 = b2[0].charAt(1);
            char ho2 = b2[1].charAt(1);
            int hor2 = ho2;

            for (int i = v1; i <= ver2; i++) {
                String key = "" + ho1;
                boatMap.put("" + i + key, b);
                b.noneHitCells = b.noneHitCells + 1;
            }

            for (int i = ho1; i < hor2; i++) {
                String key = "" + i;
                boatMap.put("" + v2 + key, b);
                b.noneHitCells = b.noneHitCells + 1;
            }

        }

        for (String s : h1) {
            Boat b = boatMap.get(s);
            if (b != null) {
                b.noneHitCells = b.noneHitCells - 1;
                resultH = resultH + 1;
            }
        }

        Set<Boat> resultBSet = new HashSet<>();

        for (Map.Entry<String, Boat> entry : boatMap.entrySet()) {
            Boat b = entry.getValue();

            if (b != null && b.noneHitCells <= 0) {
                if (b.noneHitCells == 0) {
                    resultBSet.add(b);
                }
            }
        }
        System.out.println("boatMap: " + boatMap.toString());
        System.out.println("return: " + "" + resultBSet.size() + "," + resultH);

    }


    @Data
    private static class Boat {
        int noneHitCells = 0;
    }
}
