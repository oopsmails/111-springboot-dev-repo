package com.oopsmails.springboot.kafka.config;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;

@Service
@Slf4j
public class KafkaConsumer {
    @Getter
    private CountDownLatch latch = new CountDownLatch(1);

    @Getter
    private String payload = null;

    @KafkaListener(topics="my_topic", groupId="my_group_id")
    public void getMessage(String message){
        log.info(String.format("#### -> Consumed message -> %s", message));
    }

//    @KafkaListener(topics = "${test.topic}")
//    public void receive(ConsumerRecord<?, ?> consumerRecord) {
//        log.info("received payload='{}'", consumerRecord.toString());
//        setPayload(consumerRecord.toString());
//        latch.countDown();
//    }
}
