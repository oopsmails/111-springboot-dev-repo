# SpringBoot SSO, by using JWT, through local Cookie

## Intro

You'll build 3 separated services:

1 Authentication Service: will be deployed at localhost:8080.

2 Resource Services (to simplify, we use the same code base): will be deployed at localhost:8180 and localhost:8280.


## Testing and Demo:


- Resource Service 1

`mvn clean spring-boot:run -Dserver.port=8180`

- Resource Service 2

`mvn clean spring-boot:run -Dserver.port=8280
-Dserver.port=8380`

- Testing links

http://localhost:8180/login

http://localhost:8280/

http://localhost:8380/



# Reference, THANKS!

https://hellokoding.com/hello-single-sign-on-sso-with-json-web-token-jwt-spring-boot/

