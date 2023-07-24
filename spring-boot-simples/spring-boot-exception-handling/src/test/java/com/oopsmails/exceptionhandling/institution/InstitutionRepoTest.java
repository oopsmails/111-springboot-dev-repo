package com.oopsmails.exceptionhandling.institution;

import com.oopsmails.exceptionhandling.institution.repo.jpa.InstitutionJpaRepository;
import com.oopsmails.exceptionhandling.institution.service.InstitutionService;
import com.oopsmails.exceptionhandling.util.ExtraService;
import com.oopsmails.exceptionhandling.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.TestPropertySource;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        properties = {
                "spring.h2.console.enabled=true"
        },
        classes = { //
                InstitutionRepoTest.class, //
                InstitutionRepoTest.InstitutionServiceTestConfig.class, //
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
@EnableJpaRepositories(basePackages = "com.oopsmails.exceptionhandling.institution.repo.jpa")
@EntityScan("com.oopsmails.exceptionhandling.institution.entity")
@EnableAutoConfiguration
@Slf4j
public class InstitutionRepoTest {

    @Autowired
    private InstitutionService institutionService;

    @Autowired
    private InstitutionJpaRepository institutionJpaRepository;

    @Test
    public void test_institution_withBranchCount() throws Exception {
        List<Object[]> result = institutionJpaRepository.findAllWithNumOfBranches();

        log.info("result = [{}]", JsonUtils.objectToJson(result));

        assertTrue(institutionService != null);
    }

    @TestConfiguration
    @ComponentScan(
            basePackages = {
                    "com.oopsmails.exceptionhandling.util",
                    "com.oopsmails.exceptionhandling.institution.service"
            },
            excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                    classes = {
                            ExtraService.class
                    })
    )
    public static class InstitutionServiceTestConfig {

        @Bean
        public Clock appClock() {
            LocalDateTime mockNow = LocalDateTime.of(2021, Month.NOVEMBER, 10, 10, 00, 00);
            Clock result = Clock.fixed(mockNow.atZone(ZoneId.of("Canada/Eastern")).toInstant(), ZoneId.of("Canada/Eastern"));

            return result;
        }

    }
}
