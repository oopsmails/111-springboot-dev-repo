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

    @KafkaListener(topics = "person_topic",
            containerFactory = "kafkaAvroConsumerFactoryPersonDto",
            id = "kafka-avro-basic-consumer-service-id-1")
    public void listen(@Payload PersonDto personDto, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        log.info("Receiving PersonDto from Kafka :: personDto :: {}", personDto + " from partition: " + partition);
        Person person = Person.builder()
                .firstName(personDto.getFirstName())
                .lastName(personDto.getLastName())
                .build();
        businessDomainService.postProcessReceivedMessage(person);
    }

//    @KafkaListener(topics = "${spring.kafka.topic-name}",
//            containerFactory = "kafkaStringConsumerFactory",
//            id = "avro-basic-consumer-id-2")
//    public void listenGenericString(@Payload String messageStr, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
//        logger.info("listenGenericString, Receiving message from Kafka :: messageStr :: {}", messageStr + " from partition: " + partition);
//
//        try {
//            PersonDto personDto = objectMapper.readValue(messageStr, PersonDto.class);
//            log.info("listenGenericString, converted to PersonDto: {}", personDto);
//            Person person = Person.builder()
//                    .firstName(personDto.getFirstName())
//                    .lastName(personDto.getLastName())
//                    .build();
//            log.info("listenGenericString, invoking next @Service, person: {}", personDto);
//            businessDomainService.postProcessReceivedMessage(person);
//        } catch (JsonProcessingException e) {
//            log.error("listenGenericString, cannot convert to PersonDto because this is SCHEMA!!! - cause: {}", e.getMessage());
//        }
//    }

//    @KafkaListener(topics = "${spring.kafka.topic-name}",
//            containerFactory = "kafkaAvroConsumerFactory",
//            id = "avro-basic-consumer-id-3")
//    public void listenGeneric(@Payload PersonDto personDto, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
//        logger.info("listenGeneric, Receiving message from Kafka :: personDto :: {}", personDto + " from partition: " + partition);
//
//        Person person = Person.builder()
//                .firstName(personDto.getFirstName())
//                .lastName(personDto.getLastName())
//                .build();
//        log.info("listenGeneric, invoking next @Service, person: {}", personDto);
//        businessDomainService.postProcessReceivedMessage(person);
//    }

}