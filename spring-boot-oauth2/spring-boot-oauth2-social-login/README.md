
# Spring Boot OAuth2 Social Login

- Refs:

https://www.javachinna.com/spring-boot-angular-10-user-registration-oauth2-social-login-part-1/

https://www.javachinna.com/spring-boot-angular-10-user-registration-oauth2-social-login-part-2/

https://www.javachinna.com/spring-boot-angular-10-user-registration-oauth2-social-login-part-3/

## Notes

### To Run
- Run docker-compose

albert@eosvm:~/Documents/github/springboot-dev-repo/spring-boot-oauth2/spring-boot-oauth2-social-login/docker$ docker-compose up

- Don't forget put clientId and clientSecret in application.properties, ?gmail?

- Run frontend

see /github/angular-social-login/README.md

- If using github login, then need github password. After first time login, the user is created locally. Then next time, can login with oopsmails@gmail.com/changeit

### Local

- SetupDataLoader, createUserIfNotFound

admin@oopsmails.com

admin/111111

- springboot Invalid CSRF token found for ...

could be also caused by following ... forgot revise package name

```
@SpringBootApplication(scanBasePackages = "com.oopsmails")
```

### Github

- email is null from github

go to github, Settings, Profile, Public profile, Public email

- Authorization callback URL
http://localhost:8081/login/oauth2/code/github

- name is null from github

GithubOAuth2UserInfo, 

```
	@Override
	public String getName() {
//		return (String) attributes.get("name"); // maybe this is old
		return (String) attributes.get("login");
	}
```

- User add with password: changeit

$2a$10$MD3mHoV5aH0Q/risUVlGv.XQh1p8Qnv7VbYFIgdmQLLgoFLGvKfP.

UserServiceImpl

```
private SignUpRequest toUserRegistrationObject(String registrationId, OAuth2UserInfo oAuth2UserInfo) {
		return SignUpRequest.getBuilder().addProviderUserID(oAuth2UserInfo.getId()).addDisplayName(oAuth2UserInfo.getName()).addEmail(oAuth2UserInfo.getEmail())
				.addSocialProvider(GeneralUtils.toSocialProvider(registrationId)).addPassword("changeit").build();
	}
```


## How to create app with Google, Facebook, Linkedin and Github

- Ref:

https://www.javachinna.com/spring-boot-user-registration-and-oauth2-social-login-with-facebook-google-linkedin-and-github-part-1/#Configure_Google,_Facebook,_Github_and_LinkedIn_for_Social_Login_in_Your_Spring_Boot_App


### Facebook
Here are the steps you need to follow to configure Facebook for social login:

- Go to https://developers.facebook.com and register for a developer account, if you haven’t already done so.
Head over to the Facebook app dashboard: https://developers.facebook.com/apps.
- Create a Facebook app. Instructions for creating a Facebook application can be found here: https://developers.facebook.com/docs/apps/register.
- Once you’ve created your Facebook app, go to the app dashboard, click the Settings link on the left-hand side, and select the Basic submenu.
- Save the App ID and App Secret values. You’ll need them later.
- You’ll also need to add a Facebook Login product. From the left menu, click the + sign next to products and add a Facebook Login product.
- Fill in the Authorized redirect URIs field to include the redirect URI to your app: http://<your-domain>/login/oauth2/code/facebook
- Save changes

Note: To work with localhost, The facebook app should be in development mode and ” Valid OAuth Redirect URIs” should be blank as shown below:


### Google
Here are the steps you need to follow to configure Google for social login:

- Go to https://console.developers.google.com/ and register for a developer account.
- Create a Google API Console project.
- Once your Google App is open, click on the Credentials menu and then Create Credentials followed by OAuth client ID.
- Select Web Application as the Application type.
- Give the client a name.
- Fill in the Authorized redirect URIs field to include the redirect URI to your app: http://<your-domain>/login/oauth2/code/google
- Click Create.
- Copy the client ID and client secret, as you’ll need them later.


### Github

Here are the steps you need to follow to configure Github for social login:

- Go to  https://github.com/settings/developers and create a New OAuth app under the OAuth Apps left menu.
- Fill in the Authorization callback URL field to include the redirect URI to your app: http://<your-domain>/login/oauth2/code/github
- Click on Register Application
- Copy the client ID and client secret, as you’ll need them later.


### LinkedIn
Here are the steps you need to follow to configure LinkedIn for social login:

- Go to  https://www.linkedin.com/developers/apps and create a new app.
- Go to My Apps and select the created app.
- Copy the client ID and client secret, as you’ll need them later.
- Click on the Auth tab and Fill in the Redirect URLs under OAuth 2.0 settings to include the redirect URI to your app: http://<your-domain>/login/oauth2/code/linkedin


