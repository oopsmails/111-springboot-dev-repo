package com.oopsmails.generaljava.pattern;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@Slf4j
public class PatternCollection {
    public boolean validateSql(String input) {
        String regex = "[^a-zA-Z0-9\\s+]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return !matcher.find(); // note ! here
    }

    public boolean digitOnly(String input) {
        String regex = "^[0-9]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }

    public boolean bigDecimalQuickTest(String input, String decimalPattern, String integerPattern) {
        BigDecimal number = null;

        if (input.matches(decimalPattern)) {
            try {
                number = new BigDecimal(input);
                log.info("BigDecimal number = {}", number);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        } else if (input.matches(integerPattern)) {
            try {
                number = new BigDecimal(input);
                log.info("BigDecimal number = {}", number);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean bigDecimalPatternEn(String input) {
        BigDecimal number = null;

        String decimalPattern = "^[0-9]{1,9}(\\.[0-9]{1,3})?$";
        String integerPattern = "^[0-9]{1,9}$";

        if (input.matches(decimalPattern)) {
            try {
                number = new BigDecimal(input);
                log.info("BigDecimal number = {}", number);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        } else if (input.matches(integerPattern)) {
            try {
                number = new BigDecimal(input);
                log.info("BigDecimal number = {}", number);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean bigDecimalPatternFr(String input) {
        BigDecimal number = null;

        String decimalPattern = "^[0-9]{1,9}(,[0-9]{1,3})?$";
        String integerPattern = "^[0-9]{1,9}$";

        if (input.matches(decimalPattern)) {
            try {
                String fixedInput = input.replace(',', '.');
                number = new BigDecimal(fixedInput);
                log.info("BigDecimal number = {}", number);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        } else if (input.matches(integerPattern)) {
            try {
                number = new BigDecimal(input);
                log.info("BigDecimal number = {}", number);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        } else {
            return false;
        }
    }
}
