package com.oopsmails.service;

import com.oopsmails.avro.dto.PersonDto;
import com.oopsmails.domain.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);

    @Autowired
    BusinessDomainService businessDomainService;

    @KafkaListener(topics = "${spring.kafka.topic-name}",
            containerFactory = "kafkaListenerContainerFactory",
            id = "mainListener")
//    @KafkaListener(topics = "${spring.kafka.topic-name}")
    public void listen(@Payload PersonDto personDto, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        logger.info("Receiving message from Kafka :: personDto :: {}", personDto + " from partition: " + partition);
        Person person = Person.builder()
                .firstName(personDto.getFirstName().toString())
                .lastName(personDto.getLastName().toString())
                .build();
        businessDomainService.postProcessReceivedMessage(person);
    }

//    @KafkaListener(topics = "${spring.kafka.topic-name}",
//            containerFactory = "consumerFactory",
//            id = "mainListener")
////    @KafkaListener(topics = "${spring.kafka.topic-name}")
//    public void listenGeneric(@Payload String personDto, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
//        logger.info("Receiving message from Kafka :: personDto :: {}", personDto + " from partition: " + partition);
////        businessDomainService.postProcessReceivedMessage(person);
//    }

}