package com.oopsmails.springboot.kafka.admin;

import com.oopsmails.avro.dto.PersonDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaAvroConsumer {
    @KafkaListener(topics = "person_topic",
            containerFactory = "kafkaAvroConsumerFactoryPersonDto",
            id = "kafka-admin-avro-consumer-id-1")
    public void listen(@Payload PersonDto personDto, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        log.info("Receiving message from Kafka :: personDto :: {}", personDto + " from partition: " + partition);
    }
}
