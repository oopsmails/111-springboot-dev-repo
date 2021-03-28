package com.oopsmails.springboot.javamain.date;

import com.oopsmails.springboot.javamain.SpringBootJavaGenericTestBase;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.ValueRange;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = { //
        TempSpringBootTest.TempSpringBootTestConfig.class, //
})
public class TempSpringBootTest extends SpringBootJavaGenericTestBase {
    @Autowired
    private Clock appClock;

    @Autowired
    private DateCalculator dateCalculator;


    @Test
    public void testDates() throws Exception {
        LocalDate d = LocalDate.of(2016, 12, 10);
        ValueRange r = d.range(ChronoField.DAY_OF_MONTH);
        System.out.printf("DAY_OF_MONTH: %s\n", r);
        System.out.println("------------------------------------------------------------------------------");

        String yearMonthStr = YearMonth.from(
                LocalDate.parse("2017-01-15")
        )
                .plusMonths(1)
                .format(DateTimeFormatter.ofPattern("MMM-uuuu", Locale.US));
        System.out.printf("yearMonthStr: %s\n", yearMonthStr);
        System.out.println("------------------------------------------------------------------------------");

        LocalDate localDate = LocalDate.now(appClock);
        LocalDate firstDate = localDate.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate lastDate = localDate.with(TemporalAdjusters.lastDayOfMonth());
        System.out.printf("firstDate =  %s, lastDate =  %s\n", firstDate, lastDate);
        System.out.println("------------------------------------------------------------------------------");

        ZonedDateTime zonedDateTimeNow = ZonedDateTime.now(appClock);
        ZonedDateTime firstDayOfMonth = zonedDateTimeNow.with(TemporalAdjusters.firstDayOfMonth());
        ZonedDateTime lastDayOfMonth = zonedDateTimeNow.with(TemporalAdjusters.lastDayOfMonth());
        System.out.printf("firstDayOfMonth =  %s, lastDayOfMonth =  %s\n", firstDayOfMonth, lastDayOfMonth);
        System.out.println("------------------------------------------------------------------------------");

        ZonedDateTime zonedDateTime3YearsBack = zonedDateTimeNow.minusYears(3L);
        System.out.printf("zonedDateTimeNow =  %s, zonedDateTime3YearsBack =  %s\n", zonedDateTimeNow, zonedDateTime3YearsBack);
        System.out.println("------------------------------------------------------------------------------");
    }

    @Test
    @Disabled("could fail due to forEach might not be in sequence")
    public void testIsInTheMonth() throws Exception {
        ZonedDateTime zonedDateTimeNow = ZonedDateTime.now(appClock);

        StringBuilder actualResult = new StringBuilder();
        Map<String, ZonedDateTime> testMap = new HashMap<>();
//        Map<String, ZonedDateTime> testMap = new MultiValueMap<>();

        testMap.put("2018-02", zonedDateTimeNow);

        ZonedDateTime testDate = ZonedDateTime.parse("2011-02-25T00:00:00-05:00");
        testMap.put("2018-03", testDate);

        testDate = ZonedDateTime.parse("2019-02-25T00:00:00-05:00");
        testMap.put("2018-04", testDate);

        testMap.forEach((theMonth, dateToCheck) -> {
            boolean isIn = dateCalculator.isInTheMonth(theMonth, dateToCheck);
            System.out.println("====> dateCalculator checking: " + dateToCheck + (isIn ? " is in " : " is NOT in ") + theMonth);
            actualResult.append(isIn ? "1" : "0");
        });

        String expectResult = "100";

        // forEach cannot guarantee the order!!!
        assertEquals("Result verifying ...", expectResult, actualResult.toString());
        System.out.println("------------------------------------------------------------------------------");
    }

    @Test
    public void testGetMonthStrings() throws Exception {
        ZonedDateTime zonedDateTimeNow = ZonedDateTime.now(appClock);
        ZonedDateTime zonedDateTime3YearsBack = zonedDateTimeNow.minusYears(3L);
        List<String> result = dateCalculator.getMonthStrings(zonedDateTime3YearsBack, zonedDateTimeNow);

//        ZonedDateTime fromDate = ZonedDateTime.parse("2011-02-25T00:00:00-05:00");
//        ZonedDateTime toDate = ZonedDateTime.parse("2019-06-01T00:00:00-05:00");
//        List<String> result = dateCalculator.getMonthStrings(fromDate, toDate);
        result.forEach(System.out::println);
        System.out.println("------------------------------------------------------------------------------");
    }

    @TestConfiguration
    public static class TempSpringBootTestConfig {
        @Bean
        public Clock appClock() {
            LocalDateTime mockNow = LocalDateTime.of(2018, Month.FEBRUARY, 20, 10, 00, 20);
            Clock result = Clock.fixed(mockNow.atZone(ZoneId.of("Canada/Eastern")).toInstant(), ZoneId.of("Canada/Eastern"));

            return result;
        }
    }
}
