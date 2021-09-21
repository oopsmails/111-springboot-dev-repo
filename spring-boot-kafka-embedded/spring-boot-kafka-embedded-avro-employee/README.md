
# Guide to Spring Cloud Stream with Kafka, Apache Avro and Confluent Schema Registry

- Ref:
https://www.baeldung.com/spring-cloud-stream-kafka-avro-confluent


Kafka represents all data as bytes, so it's common to use an external schema and serialize and deserialize into bytes according to that schema.

Each producer will know the schema it's producing with, and each consumer should be able to either consume data in ANY format or should have a specific schema it prefers to read in. The producer consults the registry to establish the correct ID to use when sending a message. The consumer uses the registry to fetch the sender's schema.

When the consumer knows both the sender's schema and its own desired message format, the Avro library can convert the data into the consumer's desired format.

One strength of Avro is its support for evolving messages written in one version of a schema into the format defined by a compatible alternative schema.






