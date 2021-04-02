package com.oopsmails.springboot.kafka.admin;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {
    
    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;
    
    @Value(value = "${message.topic.name}")
    private String topicName;

    @Value(value = "${partitioned.topic.name}")
    private String partionedTopicName;

    @Value(value = "${filtered.topic.name}")
    private String filteredTopicName;

    @Value(value = "${greeting.topic.name}")
    private String greetingTopicName;
    
    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }
    
    @Bean
    public NewTopic topicOops() {
         return new NewTopic(topicName, 1, (short) 1);
    }
    
    @Bean
    public NewTopic topicPartioned() {
         return new NewTopic(partionedTopicName, 6, (short) 1);
    }
    
    @Bean
    public NewTopic topicFiltered() {
         return new NewTopic(filteredTopicName, 1, (short) 1);
    }
    
    @Bean
    public NewTopic topicGreeting() {
         return new NewTopic(greetingTopicName, 1, (short) 1);
    }
}
