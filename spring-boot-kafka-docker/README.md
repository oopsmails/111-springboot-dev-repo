# spring-boot-kafka-docker

Initially from https://morioh.com/p/2bd3c2db1555, THANKS!

https://github.com/techtter/springboot-kafka-docker


## docker-compose command

```
docker-compose up # first time

docker-compose start

docker-compose stop

docker rm $(docker ps -a -q)


# Use -f to specify name and path of one or more Compose files, e.g
docker-compose -f docker-compose.yml -f docker-compose.admin.yml run backup_db

docker-compose -f docker-compose.yml 

```

## About wurstmeister kafka image

https://github.com/wurstmeister/kafka-docker

- Start a cluster:

docker-compose up -d

- Add more brokers:

docker-compose scale kafka=3

- Destroy a cluster:

docker-compose stop

- If you need to use specific ports and broker ids, modify the docker-compose configuration accordingly, e.g. docker-compose-single-broker.yml:
  
 docker-compose -f docker-compose-single-broker.yml up

- Automatically create topics
  If you want to have kafka-docker automatically create topics in Kafka during creation, a KAFKA_CREATE_TOPICS environment variable can be added in docker-compose.yml.
  
  Here is an example snippet from docker-compose.yml:
  
      environment:
        KAFKA_CREATE_TOPICS: "Topic1:1:3,Topic2:1:1:compact"
  Topic 1 will have 1 partition and 3 replicas, Topic 2 will have 1 partition, 1 replica and a cleanup.policy set to compact. Also, see FAQ: Topic compaction does not work



## Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.3.1.RELEASE/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.3.1.RELEASE/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.3.1.RELEASE/reference/htmlsingle/#boot-features-developing-web-applications)
* [Spring for Apache Kafka](https://docs.spring.io/spring-boot/docs/2.3.1.RELEASE/reference/htmlsingle/#boot-features-kafka)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)



