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
            containerFactory = "kafkaConsumerFactoryAvro",
            id = "kafka-admin-consumer-id-avro")
    public void listenAvro(@Payload PersonDto personDto,
                           @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        log.info("### -> Receiving PersonDto from Kafka :: personDto :: {}", personDto + " from partition: " + partition);
    }

    @KafkaListener(topics = "person_topic",
            containerFactory = "kafkaConsumerFactoryString",
            id = "kafka-admin-consumer-id-string")
    public void listenString(@Payload String payloadString,
                             @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        log.info("### -> Receiving String from Kafka :: this is Schema!!! :: {}", payloadString + " from partition: " + partition);
    }
}
