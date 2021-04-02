**This is a practical copy from https://www.baeldung.com/spring-kafka**

### Relevant articles

- [Intro to Apache Kafka with Spring](http://www.baeldung.com/spring-kafka)

# Spring Kafka

This is a simple Spring Boot app to demonstrate sending and receiving of messages in Kafka using spring-kafka.

## Configuring Topics

- Creating Topics using Spring AdminClient in Kafka, we can now create topics programmatically.

We need to add the KafkaAdmin Spring bean, which will automatically add topics for all beans of type **org.apache.kafka.clients.admin.NewTopic**:

```
@Configuration
public class KafkaTopicConfig {

    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }
    
    @Bean
    public NewTopic topic1() {
         return new NewTopic("baeldung", 1, (short) 1);
    }
}
```

