
# Run

- Ref

https://dzone.com/articles/ports-and-adapters-architecture-with-kafka-avro-and-spring-boot

https://betterprogramming.pub/adding-schema-registry-to-kafka-in-your-local-docker-environment-49ada28c8a9b

## Run docker-compose

```
sudo

docker-compose -f docker-compose-confluentinc-20210920-withSchemaRegistry.yaml up -d
docker-compose -f docker-compose-confluentinc-20210920-withSchemaRegistry.yaml down

docker-compose -f /home/albert/Documents/sharing/github/springboot-dev-repo/spring-boot-kafka-docker/docker-compose-confluentinc-20210920-withSchemaRegistry.yaml up -d
docker-compose -f /home/albert/Documents/sharing/github/springboot-dev-repo/spring-boot-kafka-docker/docker-compose-confluentinc-20210920-withSchemaRegistry.yaml down

docker-compose -f /home/albert/Documents/sharing/github/springboot-dev-repo/spring-boot-kafka-docker/docker-compose-confluentinc-20210920-withSchemaRegistry.yaml start
docker-compose -f /home/albert/Documents/sharing/github/springboot-dev-repo/spring-boot-kafka-docker/docker-compose-confluentinc-20210920-withSchemaRegistry.yaml stop

```

## Kafka Commands

./bin/kafka-console-producer --broker-list localhost:9092 --topic test --property "parse.key=true" --property "key.separator=:"

docker exec -it broker bash <-------- use this

```
kafka-console-producer --broker-list localhost:9092 \
--topic person_topic \
--property "parse.key=true" \
-- property "key.separator=:"

>key-good:{ "idempotencyKey": "3cc52d97-c0e3-4b84-b220-dbf4ac352dbc", "amount": 188.88, "initiatedOn": "2021-05-23 23:59:37"}
org.apache.kafka.common.KafkaException: No key found on line 1: key-good:{ "idempotencyKey": "3cc52d97-c0e3-4b84-b220-dbf4ac352dbc", "amount": 188.88, "initiatedOn": "2021-05-23 23:59:37"}
at kafka.tools.ConsoleProducer$LineMessageReader.readMessage(ConsoleProducer.scala:290)
at kafka.tools.ConsoleProducer$.main(ConsoleProducer.scala:51)
at kafka.tools.ConsoleProducer.main(ConsoleProducer.scala)

```

kafka-console-producer --broker-list localhost:9092 \
--topic person_topic \
--property "parse.key=false"

{ "idempotencyKey": "3cc52d97-c0e3-4b84-b220-dbf4ac352dbc", "amount": 188.88, "initiatedOn": "2021-05-23 23:59:37"}

{"firstName": "Golden", "lastName": "Bruen"}


kafka-topics --bootstrap-server :9092 --delete --topic person_topic

http://localhost:8081/subjects

## KafkaAvroDeserializer vs StringDeserializer

- command line producer, not working, because may NOT use KafkaAvroSerializer
then message becomes "poison pill"

- Option 1: rely on Postman (real world API) to send message and can use KafkaAvroSerializer in both Producer and Consumer
- Option 2: using StringDeserializer at Consumer side and Deserialize it to Person using ObjectMapper. But, this didn't pass test.  
When producer uses KafkaAvroSerializer, consumer gets String like "Brenden".  
Need to test more!!! for example, extending KafkaAvroDeserializer in Consumer.

### More on this,

https://rmoff.net/2020/07/03/why-json-isnt-the-same-as-json-schema-in-kafka-connect-converters-and-ksqldb-viewing-kafka-messages-bytes-as-hex/

- Producer: Json String data, Consumer: Json Schema Deserializer
what about if we mix it up, and try to read JSON data using the JSON Schema deserializer (through the io.confluent.connect.json.JsonSchemaConverter converter)?

```
org.apache.kafka.connect.errors.DataException: Converting byte[] to Kafka Connect data failed due to serialization error:
at io.confluent.connect.json.JsonSchemaConverter.toConnectData(JsonSchemaConverter.java:111)
at org.apache.kafka.connect.storage.Converter.toConnectData(Converter.java:87)
at org.apache.kafka.connect.runtime.WorkerSinkTask.lambda$convertAndTransformRecord$2(WorkerSinkTask.java:492)
at org.apache.kafka.connect.runtime.errors.RetryWithToleranceOperator.execAndRetry(RetryWithToleranceOperator.java:128)
at org.apache.kafka.connect.runtime.errors.RetryWithToleranceOperator.execAndHandleError(RetryWithToleranceOperator.java:162)
... 13 more
Caused by: org.apache.kafka.common.errors.SerializationException: Error deserializing JSON message for id -1
Caused by: org.apache.kafka.common.errors.SerializationException: Unknown magic byte!
```

- Producer: Json Schema Deserializer, Consumer: Json String data

The final permutation here is trying to read JSON Schema messages using the JSON deserializer:

```
org.apache.kafka.connect.errors.DataException: Converting byte[] to Kafka Connect data failed due to serialization error:
at org.apache.kafka.connect.json.JsonConverter.toConnectData(JsonConverter.java:355)
at org.apache.kafka.connect.storage.Converter.toConnectData(Converter.java:87)                                                               
at org.apache.kafka.connect.runtime.WorkerSinkTask.lambda$convertAndTransformRecord$2(WorkerSinkTask.java:492)                               
at org.apache.kafka.connect.runtime.errors.RetryWithToleranceOperator.execAndRetry(RetryWithToleranceOperator.java:128)
at org.apache.kafka.connect.runtime.errors.RetryWithToleranceOperator.execAndHandleError(RetryWithToleranceOperator.java:162)                
... 13 more                                                                                                                          
Caused by: org.apache.kafka.common.errors.SerializationException: java.io.CharConversionException: Invalid UTF-32 character 0x27a2272 (above 0x0010ffff) at char #1, byte #7)
Caused by: java.io.CharConversionException: Invalid UTF-32 character 0x27a2272 (above 0x0010ffff) at char #1, byte #7)
```

## Postman
GET
http://localhost:8080/avro/person

POST
http://localhost:8080/avro/person

{
"firstName": "Oops",
"lastName": "Mails"
}


## Control Center UI

Go to your browser and navigate to http://localhost:9021. You should see the Control Center UI. 

create a topic: person_topic

Then, add schema, value, key(not used for now), for value vs key, see kafka-knowledge-TopicSchemaKeyVsValue.md in general-info.

```
{
	"namespace": "com.oopsmails.avro.dto",
	"type": "record",
	"name": "PersonDto",
	"fields": [
		{
			"name": "firstName",
			"type": "string",
			"doc": "the first name of a person"
		},
		{
			"name": "lastName",
			"type": "string",
			"doc": "the last name of a person"
		}	
	]
}
```

## Run application to test around

- Run
  spring-boot-kafka-avro/spring-boot-kafka-avro-basic/spring-boot-kafka-avro-basic-application/src/main/java/com/oopsmails/PortsAndAdaptersKafkaPubSubApplication.java

will see some message from producer to consumer ...

- A controller is developed for more testing

spring-boot-kafka-avro/spring-boot-kafka-avro-basic/spring-boot-kafka-avro-basic-application/src/main/java/com/oopsmails/controller/MessageProducerPersonController.java

Can trigger sending messages again by GET: http://localhost:8080/avro/person


- 20210929: 
If change from "firstName" to "fName", then, get following error in "Control Center"  
Schema being registered is incompatible with an earlier schema for subject "person_topic-value"

- To test around schema changes

modify the value avro as

```

{
  "fields": [
    {
      "doc": "the first name of a person",
      "name": "firstName",
      "type": "string"
    }
  ],
  "name": "PersonDto",
  "namespace": "com.oopsmails.exceptionhandling.avro.dto",
  "type": "record"
}

```

then, get error:

```
Caused by: org.apache.kafka.common.errors.SerializationException: Error registering Avro schema: {"type":"record","name":"PersonDto","namespace":"com.oopsmails.avro.dto","fields":[{"name":"firstName","type":"string","doc":"the first name of a person"},{"name":"lastName","type":"string","doc":"the last name of a person"}]}
Caused by: io.confluent.kafka.schemaregistry.client.rest.exceptions.RestClientException: Schema being registered is incompatible with an earlier schema for subject "person_topic-value"; error code: 409
	at io.confluent.kafka.schemaregistry.client.rest.RestService.sendHttpRequest(RestService.java:292) ~[kafka-schema-registry-client-5.5.1.jar:na]
	at io.confluent.kafka.schemaregistry.client.rest.RestService.httpRequest(RestService.java:351) ~[kafka-schema-registry-client-5.5.1.jar:na]

```

## Note:

- To play around, change the schema Compatibility Mode as None

ALL TOPICSPERSON_TOPICSCHEMAVALUE
Edit schema
Compatibility Mode

- Added a controller to re-send Person message

GET, http://localhost:8080/avro/person

- ListenableFuture

ListenableFuture<SendResult<String, PersonDto>> future = kafkaTemplate.send(topicName, personDto);

- To list all the schemas:
curl -X GET http://localhost:8081/subjects

