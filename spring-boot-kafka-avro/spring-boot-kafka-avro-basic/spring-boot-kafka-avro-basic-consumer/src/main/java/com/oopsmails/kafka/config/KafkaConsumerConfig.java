package com.oopsmails.kafka.config;

import com.oopsmails.avro.dto.PersonDto;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

@EnableKafka
@Configuration
@Slf4j
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    @Value("${schema.registry.url}")
    private String schemaRegistryUrl;

    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);

        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);

        props.put(KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistryUrl);
        props.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);
        return props;
    }

    @Bean
    public ConsumerFactory<String, PersonDto> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PersonDto> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, PersonDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
/*
    @Bean
    ConcurrentKafkaListenerContainerFactory<?, ?> genericKafkaListenerContainerFactory(
            ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
            ObjectProvider<ConsumerFactory<Object, Object>> kafkaConsumerFactory) {
        ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        configurer.configure(factory, (ConsumerFactory<Object, Object>) kafkaConsumerFactory); // ??
        factory.setRetryTemplate(retryTemplate());
        factory.setRecoveryCallback((context -> {
            if (context.getLastThrowable().getCause() instanceof RecoverableDataAccessException) {
                //here you can do your recovery mechanism where you can put back on to the topic using a Kafka producer
            } else {
                // here you can log things and throw some custom exception that Error handler will take care of ...
                throw new RuntimeException(context.getLastThrowable().getMessage());
            }
            return null;
        }));
        factory.setErrorHandler(((exception, data) -> {
//          here you can do you custom handling, I am just logging it same as default Error handler does
//          If you just want to log. you need not configure the error handler here. The default handler does it for you.
//          Generally, you will persist the failed records to DB for tracking the failed records.
            log.error("Error in process with Exception {} and the record is {}", exception, data);
        }));
        return factory;
    }

    private RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();
        // here retry policy is used to set the number of attempts to retry and what exceptions you wanted to try and what you don't want to retry.
        retryTemplate.setRetryPolicy(getSimpleRetryPolicy());
        return retryTemplate;
    }

    private SimpleRetryPolicy getSimpleRetryPolicy() {
        Map<Class<? extends Throwable>, Boolean> exceptionMap = new HashMap<>();
        exceptionMap.put(IllegalArgumentException.class, false);
        exceptionMap.put(TimeoutException.class, true);
        return new SimpleRetryPolicy(3, exceptionMap, true);
    }
*/
}