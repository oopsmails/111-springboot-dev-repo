package com.oopsmails.kafka.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oopsmails.avro.dto.PersonDto;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaAvroBasicConsumer {

    @Autowired
    @Setter
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "person_topic",
            containerFactory = "kafkaConsumerFactoryAvro",
            id = "kafka-avro-basic-consumer-id-1")
    public void listen(@Payload PersonDto personDto, @Headers MessageHeaders messageHeaders) throws JsonProcessingException {
        log.info("========> Receiving PersonDto from Kafka :: personDto :: :: {}, messageHeader :: [{}]", personDto, getMessageHeaderJson(messageHeaders));
    }

    @KafkaListener(topics = "person_topic",
            containerFactory = "kafkaConsumerFactoryAvro",
            id = "kafka-avro-basic-consumer-id-2")
    public void listenV(@Payload PersonDto personDto, @Headers MessageHeaders messageHeaders) throws JsonProcessingException {
        log.info("========> Receiving V (PersonDto) from Kafka :: personDto :: :: {}, messageHeader :: [{}]", personDto, getMessageHeaderJson(messageHeaders));
    }

    @KafkaListener(topics = "person_topic",
            containerFactory = "kafkaConsumerFactoryString",
            id = "kafka-string-basic-consumer-id-1")
    public void listenString(@Payload String msgString, @Headers MessageHeaders messageHeaders) throws JsonProcessingException {
        log.info("========> Receiving msgString (schema) from Kafka :: msgString :: {}, messageHeader :: [{}]", msgString, getMessageHeaderJson(messageHeaders));
    }

    private String getMessageHeaderJson(MessageHeaders messageHeaders) {
        String messageHeadersJson = "";
        try {
            messageHeadersJson = objectMapper.writeValueAsString(messageHeaders);
            return messageHeadersJson;
        } catch (JsonProcessingException e) {
            log.warn("Cannot get json string, exception: {}", e.getMessage());
        }
        return messageHeadersJson;
    }
}
