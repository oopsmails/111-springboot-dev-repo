package com.oopsmails.avrobasic;

import com.oopsmails.kafka.config.KafkaAvroBasicConsumer;
import com.oopsmails.kafka.config.KafkaConsumerConfig;
import com.oopsmails.service.BusinessDomainService;
import com.oopsmails.service.KafkaConsumerService;
import com.oopsmails.service.ProducerService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Scanner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        KafkaConsumerConfig.class,
        KafkaAvroBasicConsumerTest.KafkaAvroConsumerTestConfig.class
},
        properties = {
            "oops.kafka.avro.enabled=ture"
}
)
public class KafkaAvroBasicConsumerTest {

    @Autowired
    private KafkaAvroBasicConsumer kafkaAvroBasicConsumer;

//    @Test // need to start local confluent-6.2.1
    public void test_consumer() {
        Assert.assertNotNull(kafkaAvroBasicConsumer);

        Scanner in = new Scanner(System.in);
        String s = in.nextLine();
        System.out.println("Your input is: " + s);
    }

    @TestConfiguration
    public static class KafkaAvroConsumerTestConfig {
//        @MockBean
//        private ProducerService producerService;
//
//        @Bean
//        public KafkaConsumerService kafkaConsumerService() {
//            KafkaConsumerService result = new KafkaConsumerService();
//            return result;
//        }
//
//        @Bean
//        public BusinessDomainService businessDomainService() {
//            BusinessDomainService result = new BusinessDomainService();
//            return result;
//        }

        @Bean
        public KafkaAvroBasicConsumer kafkaAvroBasicConsumer() {
            KafkaAvroBasicConsumer result = new KafkaAvroBasicConsumer();
            return result;
        }
    }
}
