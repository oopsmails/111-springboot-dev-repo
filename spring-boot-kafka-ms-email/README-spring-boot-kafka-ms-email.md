# spring-boot-kafka-ms-email

## References:

https://www.sipios.com/blog-tech/emailing-microservice-apache-kafka-spring-boot

Thanks!

## Run This App

- Start: docker-compose up

`albert@eosvm:~/Documents/github-other/messaging_microservice_kafka$ docker-compose up
`

`docker-comose up`

- Stop: docker-compose stop/down

`docker-compose stop`

`docker-compose down`

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

