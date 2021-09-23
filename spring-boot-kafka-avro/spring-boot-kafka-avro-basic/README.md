
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

## Control Center UI

Go to your browser and navigate to http://localhost:9021. You should see the Control Center UI. 

create a topic: person_topic

Then, add schema, value, key

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

- To test around

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



