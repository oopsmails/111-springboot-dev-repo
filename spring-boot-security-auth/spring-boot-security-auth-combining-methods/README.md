# spring-boot-security-auth-combining-methods

- Ref:

https://www.jessym.com/articles/combining-spring-boot-auth-methods

## Combining Spring Boot Auth Methods: Redis Sessions, Basic Auth and JWTs

We'll explore three different Spring Boot authentication methods (Redis Sessions, Basic Auth and JWTs), and see how all of these can be enabled within a single application.

## The Problem and Thinking

Typical Example. I've included spring-security into my new Java application to validate incoming JWTs. By extending Spring Security's WebSecurityConfigurerAdapter, I get to use the httpSecurity.oauth2ResourceServer() DSL to set up my JWT configuration - so far, so good. However, since my new application hooks into an existing system which is configured to submit JWTs under the X-Auth-Token header instead of the Authorization header, I need to set up a custom BearerTokenResolver, which requires me to add a dependency on the spring-security-oauth2-resource-server module. Also, because existing JWTs in the system adhere to some non-conventional schema, I'm required to provide my own mapping between the JWT and the Spring authentication principal via a custom JwtAuthenticationConverter, which requires me to add a dependency on the spring-security-oauth2-jose module.

## The App

That's why, in this article, we'll have a look at the (low-level) javax.servlet.Filter API for handling our authentication logic, with the benefit of maximum customizability and minimal Maven dependencies.

We'll be looking at a very minimal Spring Boot application consisting of a simple REST controller with 3 endpoints, each one protected by a different authorization role.

- The /user endpoint (which requires the USER role) is meant for end-users; they will be expected to authenticate with session tokens which are validated through Redis

- The /admin endpoint (which requires the ADMIN role) is meant for company admins; they will be expected to authenticate with Basic Auth credentials which are compared against in-memory configuration values

- The /system endpoint (which requires the SYSTEM role) is meant for other microservices in the system cluster; they will be expected to authenticate using JWTs (JSON Web Tokens) which are validated with the (symmetric) signing key

## Testing

Not try yet!!! need to use Redis Docker in spring-boot-session-redis/docker-compose.yml

### Redis Sessions


curl -i http://localhost:8080/user

HTTP/1.1 401

To make this work, we should store a session token into our local Redis instance, and submit this token in the HTTP request. Simply connect to Redis via the CLI client (see this helper script for Docker) and execute SET 12345 jessy EX 60 to create a new session which invalidates after 60 seconds.

After doing so, the below HTTP request to /user yields the following result.

curl -i http://localhost:8080/user -H 'Authorization: Bearer 12345'

HTTP/1.1 200
{"actor":"jessy","endpoint":"USER"}


### Basic Aut

I like the Basic Auth scheme because it's one of the easiest ways to protect an application without having to build any login screens. If the browser notices that a particular resource requires Basic Auth, it automatically prompts the user with a login modal for authentication (similar to the window.alert dialog). The UX isn't the best, but it's usually good enough for internal admin pages.

Quick reminder: with Basic Auth, you submit your login credentials under the Authorization HTTP request header as Basic <token>, where token = base64_encode(username + ':' + password)

So let's see how we can grant access to the /admin endpoint with Basic Auth. For this, I will introduce an AuthServiceBasic, conceptually similar to the AuthServiceRedis we already have.

This means we're now able to access the /admin endpoint, assuming we've supplied the correct credentials from our configuration file.

curl -i http://localhost:8080/admin -u webmaster:pass123

HTTP/1.1 200
{"actor":"webmaster","endpoint":"ADMIN"}
As expected, we are greeted with the forbidden status code if we try to access one of the other endpoints with our Basic Auth credentials.

curl -i http://localhost:8080/system -u webmaster:pass123

HTTP/1.1 403


### JWTs

The final authentication scheme we'll have a look at, is JWTs or JSON Web Tokens. I don't like using JWTs for user sessions because, due to their stateless nature, they can't really be revoked in case someone's account is hacked. But I do think they're excellent for authenticating system-to-system communication (in a microservices setup, for example).

Because of the little authentication framework we've set up so far, all we need to do to support JWT authentication, is register a new AuthService instance.

As you can see, the implementation is rather straightforward. It checks whether the incoming HTTP requests presents a bearer token, and whether this bearer token represents a valid JWT, i.e.: is signed by the right key.

Use the jwt.io website to easily construct JWTs from your browser.

Attempting to access the /system endpoint with a valid JWT should yield a success response.

token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzb21lLWV4dGVybmFsLXN5c3RlbSJ9.CUaovpMiKURewcqqrc_NjQYGVB6o2DkPglMdihklqhg
curl -i http://localhost:8080/system -H "Authorization: Bearer $token"

HTTP/1.1 200
{"actor":"some-external-system","endpoint":"SYSTEM"}
Whereas the other endpoints should yield the forbidden status code.

token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzb21lLWV4dGVybmFsLXN5c3RlbSJ9.CUaovpMiKURewcqqrc_NjQYGVB6o2DkPglMdihklqhg
curl -i http://localhost:8080/user -H "Authorization: Bearer $token"

HTTP/1.1 403




