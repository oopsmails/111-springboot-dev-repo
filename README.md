# springboot-dev-repo

## spring-boot-java-main
- Test general java topics, dates, jwt ...

- Test jsonschema2pojo-maven-plugin from json schema or object

https://joelittlejohn.github.io/jsonschema2pojo/site/1.1.0/generate-mojo.html

- MethodExecutionTime, RunningTimeLoggingService
- FunctionObjectTest, JavaOptionalTest, TestReadAndWriteLock3
- FunctionParameterTest

## spring-boot-kafka-admin

- Test using springboot kafka admin to create topics

## spring-boot-kafka-docker

- Test springboot kafka template

```
docker-compose up # first time

docker-compose start

docker-compose stop

```

## spring-boot-kafka-ms-email

- Test calling ms by using kafka to send out email

- spring-boot-kafka-ms-email-files, docker-compose.yml

## spring-boot-mock-backend

- Generic mock backend, file download
- CompletableFutureUtil, OperationTaskUtil, CompletableFutureBasicSyntax, CompletableFutureCallbacks
- CompletableFutureAllOfAnyOf, CompletableFutureCombines, CompletableFutureCompose
- GitHubLookupServiceRunner, GreetingService
- GitHubUserController
- GreetingServiceRunnerTest, GitHubLookupServiceTest

## spring-boot-oauth2

### spring-boot-oauth2-openid
- Test login with OAuth2 and OpenId Connect providers

### spring-boot-oauth2-social-login

- Test login with social web sites, like github, facebook, google, linkedin

## spring-boot-security-auth

### spring-boot-security-auth-combining-methods

- Combining Spring Boot Auth Methods: Redis Sessions, Basic Auth and JWTs

## spring-boot-rest-sonarqube

For testing sonarqube

## spring-boot-session-redis

- Test Spring Session with Redis

## spring-boot-ssl-twoway

- Test setup two way SSL

## spring-boot-sso-jwt

- Test SSO with jwt

## spring-boot-sso-jwt-redis

- Test SSO and Single Log Out (SLO) with JSON Web Token (JWT), Spring Boot and Redis

- Could revise further!

## oopsmails-common

Configure to use oopsmails-common-annotation and oopsmails-common-filter

```
<dependency>
    <groupId>com.oopsmails.springboot.dev.repo</groupId>
    <artifactId>oopsmails-common-annotation</artifactId>
    <version>1.0.0</version>
</dependency>
<dependency>
    <groupId>com.oopsmails.springboot.dev.repo</groupId>
    <artifactId>oopsmails-common-domain</artifactId>
    <version>1.0.0</version>
</dependency>
<dependency>
    <groupId>com.oopsmails.springboot.dev.repo</groupId>
    <artifactId>oopsmails-common-filter</artifactId>
    <version>1.0.0</version>
</dependency>

@Import({
OopsmailsCommonLoggingConfig.class,
OopsmailsCommonAnnotationConfig.class
})

```

