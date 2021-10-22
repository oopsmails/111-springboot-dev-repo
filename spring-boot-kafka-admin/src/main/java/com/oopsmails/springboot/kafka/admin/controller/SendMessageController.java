package com.oopsmails.springboot.kafka.admin.controller;

import com.oopsmails.avro.dto.PersonDto;
import com.oopsmails.domain.model.Person;
import com.oopsmails.springboot.kafka.admin.config.MessageProducerPerson;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@Slf4j
public class SendMessageController {

    @Autowired
    private MessageProducerPerson messageProducerPerson;

    @Autowired
    private KafkaProducer<String, PersonDto> kafkaAvroProducerPersonDto;

    @PostMapping("")
    public String postMessagePerson(@RequestBody Person person) {
        log.info("postMessagePerson, person = {}", person);
        PersonDto personDto = PersonDto.newBuilder()
                .setFirstName(person.getFirstName())
                .setLastName(person.getLastName())
                .build();
        messageProducerPerson.sendMessage(personDto);
        return "Successfully sent: [" + personDto + "]";
    }

    @PostMapping("person")
    public String postMessage(@RequestBody Person person) {
        log.info("postMessagePerson, person = {}", person);
        PersonDto personDto = PersonDto.newBuilder()
                .setFirstName(person.getFirstName())
                .setLastName(person.getLastName())
                .build();

        final ProducerRecord<String, PersonDto> record = new ProducerRecord<>("person_topic", personDto);
        kafkaAvroProducerPersonDto.send(record);

        return "Successfully sent: [" + personDto + "]";
    }


}

