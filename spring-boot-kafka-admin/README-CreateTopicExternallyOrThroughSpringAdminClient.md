**This is a practical copy from https://www.baeldung.com/spring-kafka**

### Relevant articles

- [Intro to Apache Kafka with Spring](http://www.baeldung.com/spring-kafka)

# Spring Kafka

This is a simple Spring Boot app to demonstrate sending and receiving of messages in Kafka using spring-kafka.

## Case 1: As Kafka topics are not created automatically by default, this application requires that you create the following topics manually.

Create by using command-line,

```
$ bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic baeldung
$ bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 5 --topic partitioned
$ bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic filtered
$ bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic greeting
```

or, can use docker-compose file, see

**spring-boot-kafka-docker\*\*/docker-compose-wurstmeister.yaml**, *KAFKA_CREATE_TOPIC*,

```
version: '3'

services:
  zookeeper1:
    image: wurstmeister/zookeeper
    ports:
      - 2181:2181
    container_name: zookeeper

  kafka:
    image: wurstmeister/kafka
    container_name: kafka
    ports:
      - 9092:9092
    environment:
      KAFKA_ADVERTISED_HOST_NAME: localhost
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
      KAFKA_CREATE_TOPIC: "my_topic:1:3,message:1:1,greeting:1:1,filtered:1:1,partitioned:1:5"
```

When the application runs successfully, following output is logged on to console (along with spring logs):

## Case 2: But with the introduction of AdminClient in Kafka, we can now create topics programmatically.

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


- Message received from the 'baeldung' topic by the basic listeners with groups foo and bar
>Received Message in group 'foo': Hello, World!<br>
Received Message in group 'bar': Hello, World!

- Message received from the 'baeldung' topic, with the partition info
>Received Message: Hello, World! from partition: 0

- Message received from the 'partitioned' topic, only from specific partitions
>Received Message: Hello To Partioned Topic! from partition: 0<br>
Received Message: Hello To Partioned Topic! from partition: 3

- Message received from the 'filtered' topic after filtering
>Received Message in filtered listener: Hello Baeldung!

- Message (Serialized Java Object) received from the 'greeting' topic
>Received greeting message: Greetings, World!!





