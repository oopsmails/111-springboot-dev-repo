package com.oopsmails.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaListenerStringDeserializer { // might not want to separate this because cannot handle the scenario of more consumers than partitions.

    //    @KafkaListener(topics = "${spring.kafka.topic-name}",
//            containerFactory = "consumerFactory",
//            id = "stringListener")
//    @KafkaListener(id = "thing2", topicPartitions =
//            {
//                    @TopicPartition(topic = "${spring.kafka.topic-name}",
//                            partitionOffsets = @PartitionOffset(partition = "0", initialOffset = "10"))
//            })
    public void listenGeneric(@Payload String messageStr, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        log.info("Consuming (discarding to DLQ?) String message from Kafka :: personDto :: {}", messageStr + " from partition: " + partition);
    }

}
