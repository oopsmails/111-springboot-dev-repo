package com.oopsmails.generaljava.basic;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

@Slf4j
public class JavaTest {

    @Test
    public void test_replace_r() throws Exception {

        String inputString = "Replace\\r with empty string\\r here. \\r";

        // Replace "\\r" (literal) with an empty string
        String resultString = inputString.replace("\\\\r", "");

        log.info("inuptString = [{}]", inputString);
        log.info("resultString = [{}]", resultString);

        assertNotNull(resultString);
    }

}
