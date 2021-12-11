package com.oopsmails.kafka.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerInterceptor;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import java.util.Map;

@Slf4j
public class KafkaAvroBasicConsumerInterceptor<K, V> implements ConsumerInterceptor<K, V> {

    @Override
    public ConsumerRecords<K, V> onConsume(ConsumerRecords<K, V> consumerRecords) {
        log.info("onConsume ............");
        return consumerRecords;
    }

    @Override
    public void onCommit(Map<TopicPartition, OffsetAndMetadata> map) {
        log.info("onCommit ............");
    }

    @Override
    public void close() {
        log.info("close ............");
    }

    @Override
    public void configure(Map<String, ?> map) {
        log.info("configure ............");
    }
}
