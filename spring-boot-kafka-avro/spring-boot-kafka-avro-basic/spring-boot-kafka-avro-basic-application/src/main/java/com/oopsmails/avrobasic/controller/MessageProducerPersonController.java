package com.oopsmails.avrobasic.controller;

import com.oopsmails.service.BusinessDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
}

