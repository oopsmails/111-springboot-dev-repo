package com.oopsmails.springboot.kafka.admin;

import org.apache.kafka.clients.admin.NewTopic;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
@ConfigurationProperties("kafka")
public class KafkaTestContainersLiveTest {

    @Autowired
    KafkaTemplate<String, Greeting> greetingKafkaTemplate;

//    @Autowired
//    private KafkaConsumer consumer;
//
//    @Autowired
//    private KafkaProducer producer;

    @Autowired
    private KafkaApplication.MessageListener messageListener;

    @Autowired
    private KafkaApplication.MessageProducer messageProducer;


    @Autowired
    private NewTopic topicGreeting;

    @Test
    public void givenKafkaDockerContainer_whenSendingtoDefaultTemplate_thenMessageReceived() throws Exception {
        messageProducer.sendGreetingMessage(new Greeting("Hello", "World"));
        messageListener.getGreetingLatch().await(10, TimeUnit.SECONDS);

        assertThat(messageListener.getGreetingLatch().getCount(), equalTo(0L));

    }

//    @Test
//    @Disabled
//    public void givenKafkaDockerContainer_whenSendingtoSimpleProducer_thenMessageReceived() throws Exception {
//        producer.send(topic, "Sending with own controller");
//        consumer.getLatch().await(10000, TimeUnit.MILLISECONDS);
//
//        assertThat(consumer.getLatch().getCount(), equalTo(0L));
//        assertThat(consumer.getPayload(), containsString("embedded-test-topic"));
//    }

}