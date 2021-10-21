package com.oopsmails.springboot.kafka.admin.controller;

import com.oopsmails.domain.model.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
@Slf4j
public class SendMessageController {
    @PostMapping("")
    public void postMessagePerson(@RequestBody Person person) {
        log.info("postMessagePerson ...... ......");
    }
}

