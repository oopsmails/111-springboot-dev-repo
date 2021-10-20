package com.oopsmails.kafka.errorhandling;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.SerializationException;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class CustomSeekToCurrentErrorHandler extends SeekToCurrentErrorHandler {

    private static final int MAX_RETRY_ATTEMPTS = 2;
    private static Set<String> skipExceptions = new HashSet<>();

    public static Set<String> SKIP_EXCEPTIONS = new HashSet<>();
    static {
        skipExceptions.add(SerializationException.class.getName());
        SKIP_EXCEPTIONS = Collections.unmodifiableSet(skipExceptions);
    }

    @Override
    public void handle(Exception exception, List<ConsumerRecord<?, ?>> records, Consumer<?, ?> consumer, MessageListenerContainer container) {
        try {
//            this.doSeeks(records, consumer);
            if (!records.isEmpty()) {
                log.warn("Exception: {} occurred with message: {}", exception, exception.getMessage());
                super.handle(exception, records, consumer, container);
            } else if (SKIP_EXCEPTIONS.contains(exception.getClass().getName())) {
                log.warn("SKIP_EXCEPTIONS, Exception: {} occurred with message: {}", exception, exception.getMessage());
//                this.doSeeks(records, consumer);
            } else {
                log.warn("unknown skip, Exception: {} occurred with message: {}", exception, exception.getMessage());
//                this.doSeeks(records, consumer);
            }

        } catch (SerializationException e) {
            log.warn("SerializationException Exception: {} occurred with message: {}", e, e.getMessage());
        }
    }

    private void doSeeks(List<ConsumerRecord<?, ?>> records, Consumer<?, ?> consumer) {
        Map<TopicPartition, Long> partitions = new LinkedHashMap<>();
        AtomicBoolean first = new AtomicBoolean(true);
        records.forEach((record) -> {
            if (first.get()) {
                partitions.put(new TopicPartition(record.topic(), record.partition()), record.offset() + 1L);
            } else {
                partitions.computeIfAbsent(new TopicPartition(record.topic(), record.partition()), (offset) -> {
                    return record.offset();
                });
            }

            first.set(false);
        });
        partitions.forEach(consumer::seek);
    }
}
