
# Spring Boot OAuth2 Social Login

- Refs:

https://www.javachinna.com/spring-boot-angular-10-user-registration-oauth2-social-login-part-1/

https://www.javachinna.com/spring-boot-angular-10-user-registration-oauth2-social-login-part-2/

https://www.javachinna.com/spring-boot-angular-10-user-registration-oauth2-social-login-part-3/

## Notes

### To Run
- Run docker-compose

albert@eosvm:~/Documents/github/springboot-dev-repo/spring-boot-oauth2/spring-boot-oauth2-social-login/docker$ docker-compose up

- Don't forget put clientId and clientSecret in application.properties

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
