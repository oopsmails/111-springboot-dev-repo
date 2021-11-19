package com.oopsmails.springboot.kafka.admin.rebalance;

import java.util.Properties;

/**
 * ref:
 * https://www.logicbig.com/tutorials/misc/kafka/consumer-rebalance-listener-example.html
 * <p>
 * The interface ConsumerRebalanceListener is a callback interface that the user can implement to listen to the events when partitions rebalance is triggered.
 */
public class ExampleConfig {

    public static final String BROKERS = "localhost:9092";

    public static Properties getProducerProps() {
        Properties props = new Properties();
        props.put("bootstrap.servers", BROKERS);
        props.put("acks", "all");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return props;
    }

    public static Properties getConsumerProps() {
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", BROKERS);
        props.setProperty("group.id", "testGroup");
        props.setProperty("enable.auto.commit", "false");
        props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        return props;
    }

}
