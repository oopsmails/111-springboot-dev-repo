package com.oopsmails.springboot.mockbackend.health;

import com.oopsmails.springboot.mockbackend.exception.OopsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;

@Component
@Slf4j
public class ServerHealthIndicator implements HealthIndicator {

    private String ip;
    private LocalDateTime startDateTime;

    @PostConstruct
    public void postConstruct() {
        startDateTime = LocalDateTime.now();

        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            this.ip = inetAddress.getHostAddress();
        } catch (UnknownHostException e) {
            log.error("Failed to get host IP address, e: {}", e);
            throw new OopsException("Failed to get host IP address, " + e.getMessage(), e);
        }
    }

    @Override
    public Health health() {
        return Health.status(ip == null || startDateTime == null ? "DOWN" : "UP")
                .withDetail("server ip", ip)
                .withDetail("server started at", startDateTime)
                .build();
    }
}
