package com.oopsmails.springboot.kafka.admin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
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
    private KafkaApplication.MessageListener messageListener;

    @Autowired
    private KafkaApplication.MessageProducer messageProducer;

    @Test
    public void givenKafkaDockerContainer_whenSendingtoGreetingTopic_thenMessageReceived() throws Exception {
        messageProducer.sendGreetingMessage(new Greeting("Hello", "World"));
        messageListener.getGreetingLatch().await(10, TimeUnit.SECONDS);

        assertThat(messageListener.getGreetingLatch().getCount(), equalTo(0L));
    }

    @Test
//    @Disabled
    public void givenKafkaDockerContainer_whenSendingtoFilteredTopic_thenMessageReceived() throws Exception {
        // setRecordFilterStrategy record.value().contains("World")
        messageProducer.sendMessageToFiltered("Hello Oopstopic!");
        messageProducer.sendMessageToFiltered("Hello World!");
        messageListener.getFilterLatch().await(10, TimeUnit.SECONDS);

        assertThat(messageListener.getFilterLatch().getCount(), equalTo(1L)); // Hello World was filtered, leaving 1L???
    }

    @Test
    public void givenKafkaDockerContainer_whenSendingtoPartitionTopic_thenMessageReceived() throws Exception {
        // partitions = {"0", "3"}
        for (int i = 0; i < 5; i++) {
            messageProducer.sendMessageToPartion("Hello To Partitioned Topic!", i);
        }
        messageListener.getPartitionLatch().await(10, TimeUnit.SECONDS);

        assertThat(messageListener.getPartitionLatch().getCount(), equalTo(0L));
    }
}