package com.oopsmails.avrobasic;

import com.oopsmails.domain.model.Person;
import com.oopsmails.service.BusinessDomainService;
import com.oopsmails.service.KafkaConsumerService;
import com.oopsmails.service.KafkaProducerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

//@SpringBootTest
//@DirtiesContext
//@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:4092", "port=4092" })
public class PortsAndAdaptersKafkaPubSubApplicationTest {
    @Autowired
    private BusinessDomainService businessDomainService;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Autowired
    private KafkaConsumerService kafkaConsumerService;

    @Value("${message.test.topic.name}")
    private String topic;

//    @Test
    public void givenEmbeddedKafkaBroker_whenSendingToTopic_thenMessageReceived() throws Exception {
        final Person expected = Person.builder()
                .firstName("Oops")
                .lastName("Mails")
                .build();

        kafkaProducerService.sendMessage(expected);
//        kafkaConsumerService.
//        consumer.getLatch().await(10000, TimeUnit.MILLISECONDS);
//
//        assertThat(consumer.getLatch().getCount(), equalTo(0L));
//        assertThat(consumer.getPayload(), containsString(topic));
        //        assertThat(consumer.getPayload(), containsString(expected)); // why failed when running together???
    }
}
