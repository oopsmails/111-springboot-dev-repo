package com.oopsmails.springboot.mockbackend.async;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
class GeneralAsyncTest {
    private static final GeneralAsyncMock generalAsyncMock = new GeneralAsyncMock();

    @Test
    void givenHashMap_whenSumParallel_thenError() throws Exception {
        Map<String, Integer> map = new HashMap<>();
        List<Integer> sumList = generalAsyncMock.parallelSum100(map, 100);

        long count = sumList.stream().distinct().count();
        log.info("givenHashMap_whenSumParallel_thenError: sumList.stream().distinct().count() = {}", count);

        assertNotEquals(1, count);
        long wrongResultCount = sumList.stream().filter(num -> num != 100).count();

        assertTrue(wrongResultCount > 0);
    }

    @Test
    void givenConcurrentMap_whenSumParallel_thenCorrect()
            throws Exception {
        Map<String, Integer> map = new ConcurrentHashMap<>();
        List<Integer> sumList = generalAsyncMock.parallelSum100(map, 1000);

        long count = sumList.stream().distinct().count();
        log.info("givenConcurrentMap_whenSumParallel_thenCorrect: sumList.stream().distinct().count() = {}", count);

        assertEquals(1, count);
        long wrongResultCount = sumList.stream().filter(num -> num != 100).count();

        assertEquals(0, wrongResultCount);
    }

    @Test
    void givenHashMap_whenSumParallel_withLock_thenCorrect() throws Exception {
        Map<String, Integer> map = new HashMap<>();
        List<Integer> sumList = generalAsyncMock.parallelSum100_withLock(map, 100);

        long count = sumList.stream().distinct().count();
        log.info("givenHashMap_whenSumParallel_withLock_thenCorrect: sumList.stream().distinct().count() = {}", count);

        assertEquals(1, count);
        long wrongResultCount = sumList.stream().filter(num -> num != 100).count();

        assertEquals(0, wrongResultCount);
    }
}
