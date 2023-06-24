package com.oopsmails.generaljava.pattern;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
class PatternTest {
    PatternCollection patternCollection = new PatternCollection();

    @Test
    void testValidSqlPattern() {
        Map<String, Boolean> inputs = new HashMap<>();
        inputs.put("select abc > a", false);

        for (Map.Entry<String, Boolean> entry : inputs.entrySet()) {
            assertTrue(patternCollection.validateSql(entry.getKey()) == entry.getValue(), "input: " + entry.getKey());
        }
    }

    @Test
    void testDigitOnly() {
        Map<String, Boolean> inputs = new HashMap<>();
        inputs.put("1234567890", true);
        inputs.put("55235234645", true);
        inputs.put("12h3", false);

        for (Map.Entry<String, Boolean> entry : inputs.entrySet()) {
            boolean result = patternCollection.digitOnly(entry.getKey());
            assertTrue(entry.getValue().booleanValue() == result, "input: " + entry.getKey());
        }
    }

    @Test
    void bigDecimalPatternEn() {
        Map<String, Boolean> inputs = new HashMap<>();
        inputs.put("123456789.123", true);
        inputs.put("123.456.789", false);
        inputs.put("1234567890", false);
        inputs.put("12h3", false);

        for (Map.Entry<String, Boolean> entry : inputs.entrySet()) {
            boolean result = patternCollection.bigDecimalPatternEn(entry.getKey());
            assertTrue(entry.getValue().booleanValue() == result, "input: " + entry.getKey());
        }
    }

    @Test
    void bigDecimalPatternFr() {
        Map<String, Boolean> inputs = new HashMap<>();
        inputs.put("123456789,123", true);
        inputs.put("123,456,789", false);

        inputs.put("123456789.123", false);
        inputs.put("123.456.789", false);

        inputs.put("1234567890", false);
        inputs.put("12h3", false);

        for (Map.Entry<String, Boolean> entry : inputs.entrySet()) {
            boolean result = patternCollection.bigDecimalPatternFr(entry.getKey());
            assertTrue(entry.getValue().booleanValue() == result, "input: " + entry.getKey());
        }
    }

    @Test
    void bigDecimalQuickTest() {
        String decimalPattern = "^[0-9]{1,9}(\\.[0-9]{1,2})?$";
        String integerPattern = "^[0-9]{1,9}$";

        Map<String, Boolean> inputs = new HashMap<>();
        inputs.put("123456789.12", true);
        inputs.put("123456789.123", false);
        inputs.put("123.456.789", false);
        inputs.put("1234567890", false);
        inputs.put("12h3", false);

        for (Map.Entry<String, Boolean> entry : inputs.entrySet()) {
            boolean result = patternCollection.bigDecimalQuickTest(entry.getKey(), decimalPattern, integerPattern);
            assertTrue(entry.getValue().booleanValue() == result, "input: " + entry.getKey());
        }
    }
}
