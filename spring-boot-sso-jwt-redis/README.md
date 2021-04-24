
# Single Log Out (SLO) Example with JSON Web Token (JWT), Spring Boot and Redis

- https://hellokoding.com/scalable-authentication-single-sign-on-out-sso-example-with-json-web-token-jwt-cookie-redis-java-spring-boot-freemarker/

- Note: This is just for a reference ...

Need to figure out how Redis is used here. More importantly, why using JWT and Redis together.


## Intruduction

This tutorial will walk you through the steps of creating a Single Log Out (SLO) Example with JSON Web Token (JWT), Spring Boot, and Redis

### What you'll build
You'll build 3 separated services:

1 Authentication Service: will be deployed at localhost:8080.

2 Resource Services (to simplify, we use the same code base): will be deployed at localhost:8180 and localhost:8280.


- CookieUtil

JWT Token'll be saved to and extracted from browser cookies.

    - cookie.setSecure(secure): secure=true => work on HTTPS only.

    - cookie.setHttpOnly(true): invisible to JavaScript.

    - cookie.setMaxAge(maxAge): maxAge=0: expire cookie now, maxAge<0: expire cookiie on browser exit.

    - cookie.setDomain(domain): visible to domain only.

    - cookie.setPath("/"): visible to all paths.


- JwtUtil

We use JJWT to generate/parse JWT Token.

- JwtFilter

JwtFilter enforces SSO. If JWT Token's not existed (unauthenticated), redirects to Authentication Service. If JWT Token's existed (authenticated), extracts user identity and forwards the request.


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





RedisUtil, change pool hostname ...

-Dserver.port=8380

http://localhost:8180/login
http://localhost:8280/
http://localhost:8380/
