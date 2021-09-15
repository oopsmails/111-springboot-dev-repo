package com.oopsmails.springboot.mockbackend.completablefuture.greeting;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GreetingService {
    public String getGreet(String lang) throws InterruptedException {
        log.info("Task execution started.");
        Thread.sleep(2000);
        log.info("Task execution stopped.");
        if (lang.equals("EN")) {
            return "Hello";
        } else if (lang.equals("ES")) {
            return "Hola";
        } else if (lang.equals("SN")) {
            return "Ayubovan";
        } else {
            log.warn("Get invalid passed in language: {}", lang);
            throw new IllegalArgumentException("Invalid lang param.");
        }
    }
}
