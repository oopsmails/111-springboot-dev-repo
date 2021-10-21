package com.oopsmails.springboot.kafka.admin.config;

import com.oopsmails.avro.dto.PersonDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
@Slf4j
public class MessageProducerPerson {

    @Value(value = "${person.topic.name}")
    private String personTopicName;

    @Autowired
    private KafkaTemplate<String, PersonDto> kafkaAvroPersonDtoTemplate;

    public void sendMessage(PersonDto message) {
        ListenableFuture<SendResult<String, PersonDto>> future = kafkaAvroPersonDtoTemplate.send(personTopicName, message);

        future.addCallback(new ListenableFutureCallback<SendResult<String, PersonDto>>() {

            @Override
            public void onSuccess(SendResult<String, PersonDto> result) {
                System.out.println("Sent message=[" + message + "] with offset=[" + result.getRecordMetadata().offset() + "]");
                log.info("### -> Sent message=[" + message + "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }

            @Override
            public void onFailure(Throwable ex) {
                System.out.println("Unable to send message=[" + message + "] due to : " + ex.getMessage());
                log.info("### -> Unable to send message=[" + message + "] due to : " + ex.getMessage());
            }
        });
    }
}
