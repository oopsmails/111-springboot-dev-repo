package com.oopsmails.springboot.kafka.admin;

import com.oopsmails.avro.dto.PersonDto;
import com.oopsmails.springboot.kafka.admin.config.ApplicationGeneralConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Scanner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        ApplicationGeneralConfig.class,
        KafkaAvroConsumerConfig.class,
        KafkaAvroConsumerTest.KafkaAvroConsumerTestConfig.class
},
        properties = {
                "oops.kafka.avro.enabled=ture"
        }
)
public class KafkaAvroConsumerTest {

    @Autowired
    private KafkaAvroConsumer kafkaAvroConsumer;

    @Test // need to start local confluent-6.2.1
    @Ignore
    public void test_consumer() {
        Assert.assertNotNull(kafkaAvroConsumer);

        Scanner in = new Scanner(System.in);
        String s = in.nextLine();
        System.out.println("Your input is: " + s);
    }

    @TestConfiguration
    @Slf4j
    public static class KafkaAvroConsumerTestConfig {
        @Bean
        public KafkaAvroConsumer kafkaAvroConsumer() {
            KafkaAvroConsumer result = new KafkaAvroConsumer();
            return result;
        }

        @KafkaListener(topics = "person_topic",
                containerFactory = "kafkaConsumerFactoryString",
                id = "kafka-admin-consumer-id-string-test")
        public void listenString(@Payload String payloadString,
                                 @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
            log.info("### -> IN KafkaAvroConsumerTest, Receiving String from Kafka :: this is Schema!!! :: {}",
                    payloadString + " from partition: " + partition);
        }

        @KafkaListener(topics = "person_topic",
                containerFactory = "kafkaConsumerFactoryAvro",
                id = "kafka-admin-consumer-id-Record-test")
        public void listenRecord(@Payload ConsumerRecord<String, PersonDto> record,
                                 @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
            log.info("### -> IN KafkaAvroConsumerTest, recordKey = [{}], recordValue = [{}], from partition: {}",
                    record.key(), record.value(), partition);
        }
    }
}
