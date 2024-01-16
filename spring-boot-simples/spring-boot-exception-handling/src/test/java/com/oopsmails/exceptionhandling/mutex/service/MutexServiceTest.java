package com.oopsmails.exceptionhandling.mutex.service;

import com.oopsmails.exceptionhandling.mutex.dto.Mutex;
import com.oopsmails.exceptionhandling.util.DbCommonService;
import com.oopsmails.exceptionhandling.util.ExtraService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.TestPropertySource;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "spring.h2.console.enabled=true"
        },
        classes = { //
                MutexServiceTest.class, //
                MutexServiceTest.MutexServiceTestConfig.class, //
        }
)
@PropertySource({
        "classpath:application.properties",
        "classpath:app-sql.properties",
})
@TestPropertySource(
        locations = {
                "classpath:application-test.properties"
        },
        properties = {
                "test.string=abc",
                "within.this.class.MutexServiceTest=true",
                "spring.jpa.properties.hibernate.generate_statistics=false"
        }
)
@EnableJpaRepositories(basePackages = "com.oopsmails.exceptionhandling.mutex.repository")
@EntityScan("com.oopsmails.exceptionhandling.mutex.entity")
@EnableAutoConfiguration
@Slf4j
//@DataJpaTest
@AutoConfigureDataJpa
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
public class MutexServiceTest {
    private final String NODE_1 = "NODE_1";
    private final String NODE_2 = "NODE_2";
    private final String NODE_3 = "NODE_3";
    private final String NODE_4 = "NODE_4";

    @Autowired
    private Clock appClock;

    @Value("${test.string}")
    private String testString;

    @Autowired
    private DbCommonService dbCommonService;

    @Autowired
    private IMutexService mutexService;

    @Autowired
    private TestUsageAsyncMutexService testUsageAsyncMutexService;

    @Test
    public void test_lock() throws Exception {
        final String RESOURCE_LOCK_100 = "RESOURCE_LOCK_100";
        final String RESOURCE_LOCK_101 = "RESOURCE_LOCK_101";

        mutexService.setHostName(NODE_1);
        assertTrue(this.mutexService.lock(RESOURCE_LOCK_100, Duration.ofDays(1)));

        mutexService.setHostName(NODE_2);
        assertFalse(this.mutexService.lock(RESOURCE_LOCK_100, Duration.ofDays(1)));
        assertFalse(this.mutexService.lock(RESOURCE_LOCK_100, Duration.ofMillis(1)));
        assertFalse(this.mutexService.lock(RESOURCE_LOCK_100, Duration.ofMinutes(1)));
        assertTrue(this.mutexService.lock(RESOURCE_LOCK_101, Duration.ofDays(1)));

        mutexService.setHostName(NODE_1);
        assertFalse(this.mutexService.lock(RESOURCE_LOCK_101, Duration.ofSeconds(1)));
        assertTrue(this.mutexService.lock(RESOURCE_LOCK_100, Duration.ofMillis(1)));

        mutexService.setHostName(NODE_2);
        assertTrue(this.mutexService.lock(RESOURCE_LOCK_101, Duration.ofDays(1)));

        mutexService.setHostName(NODE_1);
        assertFalse(this.mutexService.lock(RESOURCE_LOCK_101, Duration.ofMillis(1)));
    }

    @Test
    public void test_update() throws Exception {
        final String RESOURCE_UPDATE_1 = "RESOURCE_UPDATE_1";

        Mutex mutex = new Mutex();
        mutex.setResourceCode(RESOURCE_UPDATE_1);
        mutex.setNodeId(NODE_1);
        LocalDateTime lockUntil = LocalDateTime.now().plusMinutes(5);
        mutex.setLockUntil(lockUntil);
        mutex = this.mutexService.save(mutex);

        assertEquals(RESOURCE_UPDATE_1, mutex.getResourceCode());
        assertEquals(NODE_1, mutex.getNodeId());
        assertEquals(lockUntil, mutex.getLockUntil());
        assertEquals(0, mutex.getVersion());

        mutex.setNodeId(NODE_2);
        mutex = this.mutexService.save(mutex);
        assertEquals(1, mutex.getVersion());
    }

    @Test
    public void test_async_inserts() throws Exception {
        final String RESOURCE_ASYNC_INSERT_1 = "RESOURCE_ASYNC_INSERT_1";

        this.testUsageAsyncMutexService.create(RESOURCE_ASYNC_INSERT_1, NODE_1);
        while (!NODE_1.equals(this.mutexService.findByResourceCode(RESOURCE_ASYNC_INSERT_1).getNodeId())) {
            Thread.sleep(50);
        }

        Mutex mutex = new Mutex();
        mutex.setResourceCode(RESOURCE_ASYNC_INSERT_1);
        mutex.setNodeId(NODE_2);
        LocalDateTime lockUntil = LocalDateTime.now().plusMinutes(5);
        mutex.setLockUntil(lockUntil);

        try {
            mutex = this.mutexService.save(mutex);
            fail("Should have failed on DataIntegrityViolationException");
        } catch (Exception e) {
            if (!(e instanceof OopsOptimisticLockException)) {
                fail("Should have failed on ObjectOptimisticLockingFailureException");
            }
        }
    }

    @Test
    public void test_optimistic_locking() throws InterruptedException {
        final String RESOURCE_OPTIMISTIC_LOCKING_1 = "RESOURCE_OPTIMISTIC_LOCKING_1";

        Mutex mutex = new Mutex();
        mutex.setResourceCode(RESOURCE_OPTIMISTIC_LOCKING_1);
        mutex.setNodeId(NODE_1);
        LocalDateTime lockUntil = LocalDateTime.now().plusMinutes(5);
        mutex.setLockUntil(lockUntil);
        mutex = this.mutexService.save(mutex);

        assertEquals(RESOURCE_OPTIMISTIC_LOCKING_1, mutex.getResourceCode());
        assertEquals(NODE_1, mutex.getNodeId());
        assertEquals(lockUntil, mutex.getLockUntil());
        assertEquals(0, mutex.getVersion());

        mutex.setNodeId(NODE_2);
        mutex = this.mutexService.save(mutex);
        assertEquals(1, mutex.getVersion());

        this.testUsageAsyncMutexService.update(RESOURCE_OPTIMISTIC_LOCKING_1, NODE_3);
        while (!NODE_3.equals(this.mutexService.findByResourceCode(RESOURCE_OPTIMISTIC_LOCKING_1).getNodeId())) {
            Thread.sleep(50);
        }

        mutex.setNodeId(NODE_4);
        try {
            mutex = this.mutexService.save(mutex);
            fail("Should have failed on OopsOptimisticLockException");
        } catch (Exception e) {
            if (!(e instanceof OopsOptimisticLockException)) {
                fail("Should have failed on OopsOptimisticLockException");
            }
        }
    }

    @Test
    @Disabled
    public void test_lock_with_db_forDebugging() throws Exception {
        final String RESOURCE_100 = "RESOURCE_100";
        final String RESOURCE_101 = "RESOURCE_101";

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        while (!"exit".equals(input)) {
            log.info("======================= input: {} ====================", input);

            mutexService.setHostName(NODE_1);
            boolean lock1_ok = this.mutexService.lock(RESOURCE_100, Duration.ofDays(1));
            boolean lock2_ok = this.mutexService.lock(RESOURCE_100, Duration.ofDays(1));
            log.info("lock1_ok: {}, lock2_ok: {}, lock2_ok should be true because same host", lock1_ok, lock2_ok);

            input = scanner.nextLine();
        }
        log.info("======================= Done ====================");
    }

    @Test
    public void testContextLoaded() {
        LocalDateTime now = LocalDateTime.now(appClock);
        log.info("testString={}", testString);
        log.info("ChronoField.MINUTE_OF_DAY.getBaseUnit(): {}", now.minus(1470, ChronoField.MINUTE_OF_DAY.getBaseUnit()));
    }

    @TestConfiguration
    @ConditionalOnProperty(value = "within.this.class.MutexServiceTest", havingValue = "true")
    @ComponentScan(
            basePackages = {
                    "com.oopsmails.exceptionhandling.util",
                    "com.oopsmails.exceptionhandling.mutex.service"
            },
            excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                    classes = {
                            ExtraService.class
                    })
    )
    public static class MutexServiceTestConfig {

        @Bean
        public Clock appClock() {
            LocalDateTime mockNow = LocalDateTime.of(2021, Month.NOVEMBER, 10, 10, 00, 00);
            Clock result = Clock.fixed(mockNow.atZone(ZoneId.of("Canada/Eastern")).toInstant(), ZoneId.of("Canada/Eastern"));

            return result;
        }
    }
}
