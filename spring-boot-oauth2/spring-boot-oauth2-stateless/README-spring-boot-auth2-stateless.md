
# Stateless OAuth2 Social Logins with Spring Boot

## Picking Social Providers

Choosing which social login providers to support will mostly depend on your target audience. For everyday folks, it will make sense to support providers like:

* Apple
* Google
* Twitter
* Facebook

Whereas for a more technical audience, it might make sense to support providers like:

* GitHub
* GitLab

Now, it's totally possible to register a new OAuth2 application via the developer portal of any of these providers. But for this project, I've decided to use an intermediary identity/authentication service called Auth0.

They've got some nice features where:

* they'll maintain a repository of all of your application's users
* they can offer different methods of authentication for your users (e.g.: social logins, traditional passwords)
* it's easy to start prototyping, because you can just toggle which social providers you'd like to enable


## Trouble Shooting

### Spring Boot cannot start, connect refused

Cannot put _localhost_ for *auth0.issuer-uri*

```
spring.security.oauth2.client.provider.auth0.issuer-uri=https://dev-oopsmails.us.auth0.com/
```
### AUTH0: Callback URL not approved for this client application

Ref: https://stackoverflow.com/questions/59967302/callback-url-not-approved-for-this-client-application-approved-callback-urls-ca

You are most probably setting the twitter callback URL to your own domain, but in auth0 twitter login, the login callback should happen to auth0.

so change your **twitter** app callback URL to below,

https://your-tenant.auth0.com/login/callback

### Auth0: Authentication, Social, twitter

Try Connection, error,

```
{
  "error": "invalid_request",
  "error_description": "You currently have Essential access which includes access to Twitter API v2 endpoints only. If you need access to this endpoint, you’ll need to apply for Elevated access via the Developer Portal. You can learn more here: https://developer.twitter.com/en/docs/twitter-api/getting-started/about-twitter-api#v2-access-leve"
}
```

Apply the Essential access, https://developer.twitter.com/en/portal/products/elevated

Works!

```
{
  "sub": "twitter|33333333",
  "nickname": "Albert",
  "name": "Albert",
  "picture": "https://abs.twimg.com/sticky/default_profile_images/default_profile_normal.png",
  "updated_at": "2022-09-09T16:13:33.969Z"
}
```

### Back in App, error,

```
Callback URL mismatch.
The provided redirect_uri is not in the list of allowed callback URLs.
Please go to the Application Settings page and make sure you are sending a valid callback url from your application.
```


go to auth0, Applications :: dev-app :: Settings

Allowed Callback URLs

```
http://localhost:8080/callback.html, https://www.google.ca
```

## Ref

- https://www.jessym.com/articles/stateless-oauth2-social-logins-with-spring-boot



