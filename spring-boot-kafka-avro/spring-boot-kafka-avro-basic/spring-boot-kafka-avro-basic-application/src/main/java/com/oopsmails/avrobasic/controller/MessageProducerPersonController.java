package com.oopsmails.avrobasic.controller;

import com.oopsmails.domain.model.Person;
import com.oopsmails.service.BusinessDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/avro/person")
@Slf4j
public class MessageProducerPersonController {
    @Autowired
    BusinessDomainService businessDomainService;

    @GetMapping("")
    public void sendMessagePerson() {
        log.info("sendMessagePerson ...... ......");
        businessDomainService.generateAndSendMessage();
    }

    @PostMapping("")
    public void postMessagePerson(@RequestBody Person person) {
        log.info("postMessagePerson ...... ......");
        businessDomainService.sendPersonMessage(person);
    }
}

