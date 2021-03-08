# How to Run SpringKafkaDockerApplication

## Start Kafka

### Starting in VirtualBox

- First time, run docker-compose up

`docker-compose -f /home/albert/Documents/sharing/docker-compose-wurstmeister.yaml up -d`

- Stopping these kafka docker ps 

`docker stop kafka`

`docker stop zookeeper`

- Restarting these kafka docker ps

`docker start kafka`

`docker start zookeeper`

### Notes for Docker Kafka

- VB need to set port forwarding: 9092, which is from the configuration of docker-compose yml.
This is for testing from outside of container.
  
- Need to start zookeeper first then wait for a few seconds before start
kafka. Otherwise, kafka might not be started correctly.
  This will cause "Connection refused" error in Application.
  
- ALWAYS good to check status by running,

`docker ps -a`

## Configuration in Application

spring-boot-kafka-docker/src/main/resources/application.yaml

- Run ipconfig to find VirtualHost ip address:

```
Ethernet adapter VirtualBox Host-Only Network:

Connection-specific DNS Suffix  . :
Link-local IPv6 Address . . . . . : fe80::11d7:1f75:1212:e6ea%6
IPv4 Address. . . . . . . . . . . : 192.168.56.1
Subnet Mask . . . . . . . . . . . : 255.255.255.0
Default Gateway . . . . . . . . . :
```

- ElementaryOS:

`bootstarp-servers: 192.168.56.1:9092`

- Kubuntu:

## Verify Application

- Postman:

POST, http://192.168.0.96:8081/publish?message=testalbert


