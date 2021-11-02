package com.oopsmails.springboot.mockbackend.health;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
public class ServerHealthIndicator implements HealthIndicator {

    private String ip;
    private LocalDateTime startDateTime;

    @Override
    public Health health() {
        return Health.status(ip == null || startDateTime == null ? "DOWN" : "UP")
                .withDetail("server ip", ip)
                .withDetail("server started at", startDateTime)
                .build();
    }
}
