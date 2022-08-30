
# Spring Boot OAuth2 Social Login

- Refs:

https://hellokoding.com/spring-security-oauth2-and-openid-connect-in-spring-boot/

## Introduction

This tutorial walks you through the steps of creating Spring Security OAuth2 and OpenId Connect web clients in Spring Boot with Google, Github, Facebook, and Okta

The tech stack
OAuth represents Open Authorization. It is an authorization framework enabling a third-party application to obtain limited access to an HTTP service on behalf of a resource owner

OpenId Connect is built on top of OAuth2 for authentication only. While OAuth2 has no definition on the format of the token, OpenId Connect uses JWT (JSON Web Token)


## Notes

How to create with Google, Github ...., see _spring-boot-oauth2/spring-boot-oauth2-social-login/README.md_

### Github

- To run

Get github clientId and clientSecret in albertmails@gmail ... then put into _spring-boot-oauth2/spring-boot-oauth2-openid/src/main/resources/application.yml_.

Run app and open http://localhost:8081/


