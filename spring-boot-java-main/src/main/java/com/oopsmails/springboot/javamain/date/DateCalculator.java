package com.oopsmails.springboot.javamain.date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

@Component
public class DateCalculator {
    @Autowired
    private Clock appClock;

    public boolean isInTheMonth(String theMonth, ZonedDateTime dateToCheck) {
        StringBuilder monthAddedDay = new StringBuilder(theMonth);
        monthAddedDay.append("-01T00:00:00-05:00");

        ZonedDateTime refZonedDateTime = ZonedDateTime.parse(monthAddedDay.toString());
        List<ZonedDateTime> monthRange = getMonthRange(refZonedDateTime);

        return (!dateToCheck.isBefore(monthRange.get(0)) && !dateToCheck.isAfter(monthRange.get(1)));
    }

    public List<String> getMonthStrings(ZonedDateTime fromDate, ZonedDateTime toDate) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM");
        List<String> result = new ArrayList<>();
        while (!fromDate.isAfter(toDate)) {
            result.add(fmt.format(fromDate));
            fromDate = fromDate.plusMonths(1L);
        }

        return result;
    }

    private List<ZonedDateTime> getMonthRange(ZonedDateTime zonedDateTime) {
        List<ZonedDateTime> result = new ArrayList<>();

        ZonedDateTime firstDayOfMonth = zonedDateTime.with(TemporalAdjusters.firstDayOfMonth());
        result.add(firstDayOfMonth);
        ZonedDateTime lastDayOfMonth = zonedDateTime.with(TemporalAdjusters.lastDayOfMonth());
        result.add(lastDayOfMonth);

        return result;
    }
}
