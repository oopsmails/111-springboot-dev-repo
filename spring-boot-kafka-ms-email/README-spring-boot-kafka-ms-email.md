# spring-boot-kafka-ms-email

## References:

https://www.sipios.com/blog-tech/emailing-microservice-apache-kafka-spring-boot

Thanks!

## Run This App

- Start: docker-compose start/up

```
albert@eosvm:~/Documents/github/springboot-dev-repo/spring-boot-kafka-ms-email-files$ docker-compose up
```

- Stop: docker-compose stop/down

```
albert@eosvm:~/Documents/github/springboot-dev-repo/spring-boot-kafka-ms-email-files$ docker-compose down
```

- useful docker commands

```
docker-compose up

docker-compose down

docker-compose start

docker-compose stop

docker ps -a

docker volume list

docker volume prune

```

## Kafka + Admin UI (Kafdrop) for Local Development with Docker Compose

https://luisdiasdev.medium.com/kafka-kafdrop-for-local-development-with-docker-compose-f2b00a6dda8

```
version: '3'

services:
  zookeeper:
    image: bitnami/zookeeper:3-debian-10
    ports:
      - 2181:2181
    volumes:
      - zookeeper_data:/bitnami
    environment:
      - ALLOW_ANONYMOUS_LOGIN=yes

  kafka:
    image: bitnami/kafka:2-debian-10
    ports:
      - 9092:9092
    volumes:
      - kafka_data:/bitnami
    environment:
      - KAFKA_CFG_ZOOKEEPER_CONNECT=zookeeper:2181
      - ALLOW_PLAINTEXT_LISTENER=yes
    depends_on:
      - zookeeper

  kafdrop:
    image: obsidiandynamics/kafdrop
    ports:
      - 9100:9000
    environment:
      - KAFKA_BROKERCONNECT=kafka:9092
      - JVM_OPTS=-Xms32M -Xmx64M
    depends_on:
      - kafka

volumes:
  zookeeper_data:
  kafka_data
```

## Error and Solution

- ERROR: Pool overlaps with other one on this address space

That error suggest a conflict. You could list the network docker network ls and delete the existing one should you find it docker network rm my_network

- Error:

```
spring-boot-kafka-microservice-email_1  | [ERROR] No plugin found for prefix 'spring-boot' in the current project and in the plugin groups [org.apache.maven.plugins, org.codehaus.mojo] available from the repositories [local (/home/deploy/.m2/repository), central (https://repo.maven.apache.org/maven2)] -> [Help 1]
  spring-boot-kafka-microservice-email_1  | [ERROR]
```

This error is from the mis-configuration of docker-compose, Deocker

Basically,

```aidl
# error
volumes:
  - ./configuration:/home/albert/dockerdata/configuration:delegated
  - ../spring-boot-kafka-microservice-email:/home/albert/dockerdata/app:delegated
  - ~/.m2:/home/albert/dockerdata/.m2:cached
  
# correct
volumes:
  - ./configuration:/tmp/configuration:delegated
  - ../spring-boot-kafka-microservice-email:/tmp/app:delegated
  - ~/.m2:/home/deploy/.m2:cached

```
- Error: ERROR: Forbidden path outside the build context: ../spring-boot-kafka-ms-email/docker/Dockerfile ()

It is all about context,

  - in springboot-dev-repo, this is working

```
  spring-boot-kafka-ms-email:
    build:
      context: ../spring-boot-kafka-ms-email/
      dockerfile: docker/Dockerfile
```

  - in springboot-dev-repo-gradle, this is working

```
  spring-boot-kafka-microservice-email:
    build:
      context: ../spring-boot-kafka-microservice-email/
      dockerfile: ../spring-boot-kafka-microservice-email/docker/Dockerfile
```


## Concept error: 

- right side of colon is from docker container, i.e, cannot use albert, etc.

- More on docker-compose and configurations

  - the **deploy** user is defined in Dockerfile

  - nginx.conf is point to Spring application

```aidl
        location /messaging {
            set $upstream spring-boot-kafka-microservice-email;
            set $contextpath /messaging;
```

  - restructured need to be taken care


## Analysis and Verify

### Structure: the set up of the project

The distributed messaging system is comprised of the following elements:

- The reverse proxy Nginx
- A Spring Boot micro-service, called messaging-api
- Kafka, and its dependency Zookeeper
- Mailhog: a simple mail catcher that is quite handy for debugging our e-mailing application.


#### Nginx

- see ./configuration/nginx.conf
- listen 8080;
- set $contextpath /messaging;

#### 

