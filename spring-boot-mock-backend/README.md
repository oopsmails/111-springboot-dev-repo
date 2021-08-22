# spring-boot-mock-backend

## Contents

## Mock Backend MS

## 2D array replacement

## File Download Testing
http://localhost:19080/backend-mock/downloadFile?filename=testTxt

## Annotation Examples

- public @interface CryptoCheck

## Crypto Example

- Ref
https://mkyong.com/java/java-how-to-convert-bytes-to-hex/

This article shows you a few ways to convert byte arrays or byte[] to a hexadecimal (base 16 or hex) string representative.

String.format
Integer.toHexString
Apache Commons Codec – commons-codec
Spring Security Crypto – spring-security-crypto
Bitwise shifting and masking. (educational purposes)

## GenericMockController

- Usage
For testing purpose, like WireMock ...  
Example,   

http://localhost:8888/backendmock/ping, will look for *spring-boot-mock-backend/src/main/resources/data/backendmock.ping.get.data.json*

- About CORS

https://stackoverflow.com/questions/36968963/how-to-configure-cors-in-a-spring-boot-spring-security-application  

    - If you are using **Spring Security**, you can do the following to ensure that CORS requests are handled first:

```
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            // by default uses a Bean by the name of corsConfigurationSource
            .cors().and()
            ...
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("https://example.com"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
```
See Spring 4.2.x CORS for more information.  

    - Without Spring Security this will work:  

```
@Bean
public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")
            .allowedOrigins("*")
            .allowedMethods("GET", "PUT", "POST", "PATCH", "DELETE", "OPTIONS");
        }
    };
}
```

## Async and Multithreading

- Ref:
  https://spring.io/guides/gs/async-method/

- GitHubLookupService



