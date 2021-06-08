package com.oopsmails.springboot.kafka.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;

@Service
@Slf4j
@Data
public class KafkaConsumer {
    private CountDownLatch latch = new CountDownLatch(1);

    private String payload = null;

    @KafkaListener(topics = "${message.docker-topic.name}")
    public void receive(ConsumerRecord<?, ?> consumerRecord) {
        log.info("### -> received payload = '{}'", consumerRecord.toString());
        setPayload(consumerRecord.toString());
        log.info(String.format("### -> consumed message -> %s", consumerRecord.value()));
        latch.countDown();
    }
}
