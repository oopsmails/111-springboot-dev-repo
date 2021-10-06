package com.oopsmails.kafka.config;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializerConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@EnableKafka
@Configuration
@ConditionalOnProperty("oopsmails.kafka.enabled")
@Slf4j
public class OopsmailsKafkaConsumerConfig {
    @Value(value = "${oopsmails.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Value(value = "${oopsmails.kafka.consumer.group-id}")
    private String consumerGroupId;

    @Value(value = "${oopsmails.kafka.topic-name}")
    private String topicName;

    @Value(value = "${oopsmails.kafka.auto.offset.reset}")
    private String autoOffsetReset;

    @Value(value = "${oopsmails.kafka.topic.client.id}")
    private String clientId;

    @Value(value = "${oopsmails.kafka.keystore.path}")
    private String keyStorePath;

    @Value(value = "${oopsmails.kafka.topic-name}")
    private String keyStorePassword;

    @Value(value = "${oopsmails.kafka.keystore.password}")
    private String trustStorePath;

    @Value(value = "${oopsmails.kafka.truststore.password}")
    private String trustStorePassword;

    @Value(value = "${oopsmails.kafka.schema.registry.url}")
    private String schemaRegistryUrl;

    @Value(value = "${oopsmails.kafka.basic.auth.credentials.source}")
    private String basicAuthCredentialsSource;

    @Value(value = "${oopsmails.kafka.bootstrap-servers.secure:true}")
    private boolean bootstrapSecure;

    //    oopsmails.kafka.topic-name=person_topic
    //
    //    oopsmails.kafka.expected.channel=OOPSMAILS

    //    =oopsmails-local-producer
    //    oopsmails.kafka.specific.avro.reader=true
    //    =USER_INFO
    //    oopsmails.kafka.security.protocol=SSL
    //    oopsmails.kafka.keystore.type=JKS
    //    oopsmails.kafka.truststore.type=JKS

    //    oopsmails.kafka.truststore.password=changeit
    //    oopsmails.kafka.health.timeout.ms=5000

    @PostConstruct
    public void init() {
        System.setProperty("javax.net.ssl.keyStore", keyStorePath);
        System.setProperty("javax.net.ssl.keyStorePassword", keyStorePassword);
        System.setProperty("javax.net.ssl.trustStore", trustStorePath);
        System.setProperty("javax.net.ssl.trustStorePassword", trustStorePassword);
    }

    @Bean
    public Properties oopsmailsKafkaConsumerFactoryProps() {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
        properties.put(ConsumerConfig.CLIENT_ID_CONFIG, clientId);

//        properties.put("security.protocol", securityProtocol);
//        properties.put("ssl.keystore.type", securityProtocol);
//        properties.put("ssl.keystore.protocol", securityProtocol);
//        properties.put("ssl.keystore.password", securityProtocol);
//        properties.put("ssl.key.password", securityProtocol);
//        properties.put("ssl.truststore.type", securityProtocol);
//        properties.put("ssl.truststore.protocol", securityProtocol);
//        properties.put("ssl.truststore.password", securityProtocol);

        properties.put("schema.registry.url", schemaRegistryUrl);
        properties.put("oopsmails.kafka.bootstrap-servers.secure", schemaRegistryUrl);
        properties.put(KafkaAvroSerializerConfig.BASIC_AUTH_CREDENTIALS_SOURCE, basicAuthCredentialsSource);
        properties.put(KafkaAvroSerializerConfig.USER_INFO_CONFIG, basicAuthCredentialsSource);
        properties.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, basicAuthCredentialsSource);

        return properties;
    }

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(oopsmailsKafkaConsumerFactoryPropsMap());
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> concurrentKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> filteredConcurrentKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setRecordFilterStrategy(record -> {
            return true; // temp
        });
        return factory;
    }

    private Map<String, Object> oopsmailsKafkaConsumerFactoryPropsMap() {
        Map<String, Object> props = new HashMap<>();
        Properties properties = oopsmailsKafkaConsumerFactoryProps();
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            log.debug("oopsmailsKafkaConsumerFactoryPropsMap, key = {}, value = {}", entry.getKey(), entry.getValue());
            props.put(String.valueOf(entry.getKey()), entry.getValue());
        }

        return props;
    }
}
