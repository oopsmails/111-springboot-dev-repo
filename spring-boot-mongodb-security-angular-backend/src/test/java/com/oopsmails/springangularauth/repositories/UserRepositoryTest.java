package com.oopsmails.springangularauth.repositories;

import com.oopsmails.springangularauth.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { //
        UserRepositoryTest.UserRepositoryTestTestConfig.class, //
})
@Disabled("Need to run Mongodb") // Working here, but disabled all test cases in this class
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

//    @Disabled("Need to run Mongodb") // Not working here, because of "UnsatisfiedDependencyException at AutowiredAnnotationBeanPostProcessor.java:643"
//    @Tag("TEST_TAG_NEED_START_MONGO")
    @Test
    public void testFindByEmail() {
        String email = "test1@abc.com";
        User result = userRepository.findByEmail(email);
        assertEquals(result.getEmail(), is(equalTo(email)));
    }

    @Test
//    @Tag("TEST_TAG_NEED_START_MONGO")
    public void testStringReverse2() {
        String s1 = "123456781234";
        StringBuilder result = new StringBuilder();
        for (int i = s1.length() - 1; i >= 0; i--) {
            result.append((i >= 4 && i <= 7) ? "x" : s1.charAt(i));
        }
        System.out.println(result.toString());
    }

    @TestConfiguration
    public static class UserRepositoryTestTestConfig {
        @Bean
        public Clock appClock() {
            LocalDateTime mockNow = LocalDateTime.of(2018, Month.FEBRUARY, 20, 10, 00, 20);
            Clock result = Clock.fixed(mockNow.atZone(ZoneId.of("Canada/Eastern")).toInstant(), ZoneId.of("Canada/Eastern"));

            return result;
        }
    }
}
