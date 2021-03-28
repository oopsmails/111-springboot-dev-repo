
# Kafka Learning

## Springboot application will fail to start if Kafka is not up or topic is no created

https://stackoverflow.com/questions/55336461/how-can-i-create-many-kafka-topics-during-spring-boot-application-start-up

### The problem

- How can I create many kafka topics during spring-boot application start up?

I have this configuration:

```
@Configuration
public class KafkaTopicConfig {

    private final TopicProperties topics;

    public KafkaTopicConfig(TopicProperties topics) {
        this.topics = topics;
    }

    @Bean
    public NewTopic newTopicImportCharge() {
        TopicProperties.Topic topic = topics.getTopicNameByType(MessageType.IMPORT_CHARGES.name());
        return new NewTopic(topic.getTopicName(), topic.getNumPartitions(), topic.getReplicationFactor());
    }

    @Bean
    public NewTopic newTopicImportPayment() {
        TopicProperties.Topic topic = topics.getTopicNameByType(MessageType.IMPORT_PAYMENTS.name());
        return new NewTopic(topic.getTopicName(), topic.getNumPartitions(), topic.getReplicationFactor());
    }

    @Bean
    public NewTopic newTopicImportCatalog() {
        TopicProperties.Topic topic = topics.getTopicNameByType(MessageType.IMPORT_CATALOGS.name());
        return new NewTopic(topic.getTopicName(), topic.getNumPartitions(), topic.getReplicationFactor());
    }
}

```

I can add 10 differents topics to TopicProperties. And I don't want create each similar bean manually. Does some way exist for create all topic in spring-kafka or only spring?

### Option 1: If having kafka topics pre-setup and just need to connect to the topics

Use an admin client directly; you can get a pre-built properties map from Boot's KafkaAdmin.

```
@SpringBootApplication
public class So55336461Application {

    public static void main(String[] args) {
        SpringApplication.run(So55336461Application.class, args);
    }

    @Bean
    public ApplicationRunner runner(KafkaAdmin kafkaAdmin) {
        return args -> {
            AdminClient admin = AdminClient.create(kafkaAdmin.getConfig());
            List<NewTopic> topics = new ArrayList<>();
            // build list
            admin.createTopics(topics).all().get();
        };
    }
}
```


### Option 2: (May) Need to create topics by applicaiton itself

Check if they already exist, or if the partitions need to be increased, the KafkaAdmin has this logic...

```aidl

private void addTopicsIfNeeded(AdminClient adminClient, Collection<NewTopic> topics) {
    if (topics.size() > 0) {
        Map<String, NewTopic> topicNameToTopic = new HashMap<>();
        topics.forEach(t -> topicNameToTopic.compute(t.name(), (k, v) -> t));
        DescribeTopicsResult topicInfo = adminClient
                .describeTopics(topics.stream()
                        .map(NewTopic::name)
                        .collect(Collectors.toList()));
        List<NewTopic> topicsToAdd = new ArrayList<>();
        Map<String, NewPartitions> topicsToModify = checkPartitions(topicNameToTopic, topicInfo, topicsToAdd);
        if (topicsToAdd.size() > 0) {
            addTopics(adminClient, topicsToAdd);
        }
        if (topicsToModify.size() > 0) {
            modifyTopics(adminClient, topicsToModify);
        }
    }
}

private Map<String, NewPartitions> checkPartitions(Map<String, NewTopic> topicNameToTopic,
        DescribeTopicsResult topicInfo, List<NewTopic> topicsToAdd) {

    Map<String, NewPartitions> topicsToModify = new HashMap<>();
    topicInfo.values().forEach((n, f) -> {
        NewTopic topic = topicNameToTopic.get(n);
        try {
            TopicDescription topicDescription = f.get(this.operationTimeout, TimeUnit.SECONDS);
            if (topic.numPartitions() < topicDescription.partitions().size()) {
                if (LOGGER.isInfoEnabled()) {
                    LOGGER.info(String.format(
                        "Topic '%s' exists but has a different partition count: %d not %d", n,
                        topicDescription.partitions().size(), topic.numPartitions()));
                }
            }
            else if (topic.numPartitions() > topicDescription.partitions().size()) {
                if (LOGGER.isInfoEnabled()) {
                    LOGGER.info(String.format(
                        "Topic '%s' exists but has a different partition count: %d not %d, increasing "
                        + "if the broker supports it", n,
                        topicDescription.partitions().size(), topic.numPartitions()));
                }
                topicsToModify.put(n, NewPartitions.increaseTo(topic.numPartitions()));
            }
        }
        catch (@SuppressWarnings("unused") InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        catch (TimeoutException e) {
            throw new KafkaException("Timed out waiting to get existing topics", e);
        }
        catch (@SuppressWarnings("unused") ExecutionException e) {
            topicsToAdd.add(topic);
        }
    });
    return topicsToModify;
}


```

### Option 3: Can set autostartup = "false" on the listener

You can set autostartup = "false" on the listener and start it yourself (using the KafkaListenerEndpointRegistry - give the listener an id so you can get a reference to its container from the registry).

If the broker is not available, the KafkaAdmin won't create the topic; you will also need to call KafkaAdmin.initialize():

```

/**
* Call this method to check/add topics; this might be needed if the broker was not
* available when the application context was initialized, and
* {@link #setFatalIfBrokerNotAvailable(boolean) fatalIfBrokerNotAvailable} is false,
* or {@link #setAutoCreate(boolean) autoCreate} was set to false.
* @return true if successful.
* @see #setFatalIfBrokerNotAvailable(boolean)
* @see #setAutoCreate(boolean)
  */
  public final boolean initialize() {
  
```

- Thanks - so I'd have to do some sort of polling in an async task waiting for Kafka to come up and then start up the listener and initialize once it's up ? – PaulNUK Sep 24 '20 at 13:34
1
- Right; you can call kafkaAdmin.initialize() in a loop until it succeeds and then start the container. – Gary Russell Sep 24 '20 at 14:02



