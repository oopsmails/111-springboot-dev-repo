package com.oopsmails.springboot.oauth2.sociallogin.security.oauth2.user;

import com.oopsmails.springboot.oauth2.sociallogin.dto.SocialProvider;
import com.oopsmails.springboot.oauth2.sociallogin.exception.OAuth2AuthenticationProcessingException;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class OAuth2UserInfoFactory {
	public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
		log.info("####> getOAuth2UserInfo, registrationId = {}", registrationId);
		log.info("####> getOAuth2UserInfo, attributes = {}", attributes);
		if (registrationId.equalsIgnoreCase(SocialProvider.GOOGLE.getProviderType())) {
			return new GoogleOAuth2UserInfo(attributes);
		} else if (registrationId.equalsIgnoreCase(SocialProvider.FACEBOOK.getProviderType())) {
			return new FacebookOAuth2UserInfo(attributes);
		} else if (registrationId.equalsIgnoreCase(SocialProvider.GITHUB.getProviderType())) {
			return new GithubOAuth2UserInfo(attributes);
		} else if (registrationId.equalsIgnoreCase(SocialProvider.LINKEDIN.getProviderType())) {
			return new LinkedinOAuth2UserInfo(attributes);
		} else if (registrationId.equalsIgnoreCase(SocialProvider.TWITTER.getProviderType())) {
			return new GithubOAuth2UserInfo(attributes);
		} else {
			throw new OAuth2AuthenticationProcessingException("Sorry! Login with " + registrationId + " is not supported yet.");
		}
	}
}