package com.oopsmails.springboot.kafka.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaProducer {

    @Value("${message.docker-topic.name}")
    private String topicName;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void writeMessage(String msg){
        log.info("### -> writeMessage msg = '{}' ... ", msg);
        this.send(topicName, msg);
    }

    public void send(String topic, String payload) {
        log.info("### -> sending payload = '{}' to topic = '{}'", payload, topic);
        kafkaTemplate.send(topic, payload);
    }
}
