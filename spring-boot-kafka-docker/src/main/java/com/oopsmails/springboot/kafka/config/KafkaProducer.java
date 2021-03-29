package com.oopsmails.springboot.kafka.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaProducer {

    private static final String TOPIC= "my_topic";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void writeMessage(String msg){
        log.info("sending payload='{}' to topic='{}'", msg, TOPIC);
        this.kafkaTemplate.send(TOPIC, msg);
    }

}
