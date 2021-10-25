package com.oopsmails.springboot.kafka.admin;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Scanner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
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

//    @Test // need to start local confluent-6.2.1
    public void test_consumer() {
        Assert.assertNotNull(kafkaAvroConsumer);

        Scanner in = new Scanner(System.in);
        String s = in.nextLine();
        System.out.println("Your input is: " + s);
    }

    @TestConfiguration
    public static class KafkaAvroConsumerTestConfig {
        @Bean
        public KafkaAvroConsumer kafkaAvroConsumer() {
            KafkaAvroConsumer result = new KafkaAvroConsumer();
            return result;
        }
    }
}
