package com.oopsmails.springboot.mockbackend.config;

import com.oopsmails.common.logging.config.OopsmailsCommonLoggingConfig;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Import;

@SpringBootConfiguration
@Import({OopsmailsCommonLoggingConfig.class})
public class SpringBootBackendMockApplicationConfig {
}
