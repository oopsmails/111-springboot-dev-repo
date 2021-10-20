package com.oopsmails.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oopsmails.avro.dto.PersonDto;
import com.oopsmails.domain.model.Person;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);

    @Autowired
    BusinessDomainService businessDomainService;

    @Autowired
    ObjectMapper objectMapper;

//    @KafkaListener(topics = "${spring.kafka.topic-name}",
//            containerFactory = "kafkaListenerContainerFactory",
//            id = "mainListener")
////    @KafkaListener(topics = "${spring.kafka.topic-name}")
//    public void listen(@Payload PersonDto personDto, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
//        logger.info("Receiving message from Kafka :: personDto :: {}", personDto + " from partition: " + partition);
//        Person person = Person.builder()
//                .firstName(personDto.getFirstName().toString())
//                .lastName(personDto.getLastName().toString())
//                .build();
//        businessDomainService.postProcessReceivedMessage(person);
//    }

    @KafkaListener(topics = "${spring.kafka.topic-name}",
            containerFactory = "consumerFactory",
            id = "mainListener")
//    @KafkaListener(topics = "${spring.kafka.topic-name}")
    public void listenGeneric(@Payload String messageStr, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        logger.info("listenGeneric, Receiving message from Kafka :: messageStr :: {}", messageStr + " from partition: " + partition);
//        businessDomainService.postProcessReceivedMessage(person);

        try {
            PersonDto personDto = objectMapper.readValue(messageStr, PersonDto.class);
            log.info("listenGeneric, converted to PersonDto: {}", personDto);
            Person person = Person.builder()
                    .firstName(personDto.getFirstName().toString())
                    .lastName(personDto.getLastName().toString())
                    .build();
            log.info("listenGeneric, invoking next @Service, person: {}", personDto);
            businessDomainService.postProcessReceivedMessage(person);
        } catch (JsonProcessingException e) {
            log.error("listenGeneric, cannot convert to PersonDto - cause: {}", e.getMessage());
        }
    }

}