
## About Kafka Avro

- Ref:
  https://www.baeldung.com/spring-cloud-stream-kafka-avro-confluent

## Notes

Kafka represents all data as bytes, so it's common to use an external schema and serialize and deserialize into bytes according to that schema.

Each producer will know the schema it's producing with, and each consumer should be able to either consume data in ANY format or should have a specific schema it prefers to read in. The producer consults the registry to establish the correct ID to use when sending a message. The consumer uses the registry to fetch the sender's schema.

When the consumer knows both the sender's schema and its own desired message format, the Avro library can convert the data into the consumer's desired format.

One strength of Avro is its support for evolving messages written in one version of a schema into the format defined by a compatible alternative schema.



5.7. What Happened During Processing?
Let's try to understand what exactly happened with our example application:

The producer built the Kafka message using the Employee object
The producer registered the employee schema with the schema registry to get a schema version ID, this either creates a new ID or reuses the existing one for that exact schema
Avro serialized the Employee object using the schema
Spring Cloud put the schema-id in the message headers
The message was published on the topic
When the message came to the consumer, it read the schema-id from the header
The consumer used schema-id to get the Employee schema from the registry
The consumer found a local class that could represent that object and deserialized the message into it

Serialization/Deserialization Using Native Kafka Libraries
Spring Boot provides a few out of box message converters. By default, Spring Boot uses the Content-Type header to select an appropriate message converter.

In our example, the Content-Type is application/*+avro, Hence it used AvroSchemaMessageConverter to read and write Avro formats. But, Confluent recommends using KafkaAvroSerializer and KafkaAvroDeserializer for message conversion.


