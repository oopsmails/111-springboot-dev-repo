package com.oopsmails.kafka.config;

import com.oopsmails.avro.dto.PersonDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaAvroBasicConsumer {


    @KafkaListener(topics = "person_topic",
            containerFactory = "kafkaConsumerFactoryAvro",
            id = "kafka-avro-basic-consumer-id-1")
    public void listen(@Payload PersonDto personDto, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        log.info("========> Receiving PersonDto from Kafka :: personDto :: {}", personDto + " from partition: " + partition);
    }

    @KafkaListener(topics = "person_topic",
            containerFactory = "kafkaConsumerFactoryAvro",
            id = "kafka-avro-basic-consumer-id-2")
    public void listenV(@Payload PersonDto personDto, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        log.info("========> Receiving V (PersonDto) from Kafka :: personDto :: {}", personDto + " from partition: " + partition);
    }

    @KafkaListener(topics = "person_topic",
            containerFactory = "kafkaConsumerFactoryString",
            id = "kafka-string-basic-consumer-id-1")
    public void listenString(@Payload String msgString, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        log.info("========> Receiving msgString (schema) from Kafka :: msgString :: {}", msgString + " from partition: " + partition);
    }
}
