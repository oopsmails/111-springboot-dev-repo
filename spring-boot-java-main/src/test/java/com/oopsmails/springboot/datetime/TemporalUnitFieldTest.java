package com.oopsmails.springboot.datetime;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;

/**
 * https://stackoverflow.com/questions/28963110/what-is-the-difference-of-using-temporalamount-or-temporalunit-in-java-8
 * <p>
 * Duration can only handle fixed-length periods, such as "hours", "minutes", "seconds", "days" (where it assumes exactly 24 hours per day). You can't use "months" with Duration, because a month varies in length.
 * <p>
 * Period - the other common TemporalAmount implementation - represents years, months and days separately.
 * <p>
 * Personally I would recommend:
 * <p>
 * When you know the unit beforehand, use the appropriate plusXxx method, e.g. time.plusMinutes(10). That's about as easy to read as it gets.
 * When you're trying to represent "logical" calendar amounts, use Period
 * When you're trying to represent "fixed length" amounts, use Duration
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { //
        TemporalUnitFieldTest.class, //
        TemporalUnitFieldTest.TemporalUnitFieldTestConfig.class, //
})
@TestPropertySource(
        locations = {
                "classpath:application-test.properties"
        },
        properties = {
                "test.string=abc",
                "within.this.class.TemporalUnitFieldTest=true"
        }
)
@Slf4j
public class TemporalUnitFieldTest {
    @Autowired
    private Clock appClock;

    @Value("${test.string}")
    private String testString;

    @Test
    public void testDurationVsPeriod() {
        ZoneId zone = ZoneId.of("Europe/London");
        // At 2015-03-29T01:00:00Z, Europe/London goes from UTC+0 to UTC+1
        LocalDate transitionDate = LocalDate.of(2015, 3, 29);
        ZonedDateTime start = ZonedDateTime.of(transitionDate, LocalTime.MIDNIGHT, zone);
        ZonedDateTime endWithDuration = start.plus(Duration.ofDays(1));
        ZonedDateTime endWithPeriod = start.plus(Period.ofDays(1));
        System.out.println(endWithDuration); // 2015-03-30T01:00+01:00[Europe/London]
        System.out.println(endWithPeriod);   // 2015-03-30T00:00+01:00[Europe/London]
    }

    @Test
    public void testTemporalUnitField() {
        LocalDateTime now = LocalDateTime.now(appClock);
        log.info("testString={}", testString);
        log.info("ChronoField.MINUTE_OF_DAY.getBaseUnit(): {}", now.minus(1470, ChronoField.MINUTE_OF_DAY.getBaseUnit()));
    }

    @TestConfiguration
    @ConditionalOnProperty(value = "within.this.class.TemporalUnitFieldTest", havingValue = "true")
    public static class TemporalUnitFieldTestConfig {
        @Bean
        public Clock appClock() {
            LocalDateTime mockNow = LocalDateTime.of(2021, Month.NOVEMBER, 10, 10, 00, 00);
            Clock result = Clock.fixed(mockNow.atZone(ZoneId.of("Canada/Eastern")).toInstant(), ZoneId.of("Canada/Eastern"));

            return result;
        }
    }
}
