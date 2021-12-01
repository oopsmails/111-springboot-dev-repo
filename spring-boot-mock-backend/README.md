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

### GitHubUserController

- Ref:
  https://spring.io/guides/gs/async-method/

- GitHubLookupService
- Spring Bean returning Executor vs ForkJoinPool
  - executor.execute(): will use ALL thread from poll
  - forkJoinPool.submit(() -> { list.parallelStream().map(...)}): will execute the first item in main thread and other items in thread from pool.

### Test CompletableFuture

- Run MockDelapServiceText.testFindAllIfParallel()
Comparing with in application.properties file,

`data.loader.in.parallel=MockDelayServiceOperationContext`
and
`data.loader.in.parallel=`

to compare performances between sequentially or in parallel ....

- Run CompletableFutureBasicSyntax
- Run CompletableFutureCombines
- Run CompletableFutureCompose
- Run CompletableFutureAllOfAnyOf

### ListenableFuture example

see spring-boot-kafka-avro/spring-boot-kafka-avro-basic/spring-boot-kafka-avro-basic-producer/src/main/java/com/oopsmails/service/KafkaProducerService.java

ListenableFuture<SendResult<String, PersonDto>> future = kafkaTemplate.send(topicName, personDto);


### Java Generic super vs extends

OperationContext

```
    private List<? super OperationTask<?, ?>> operationTasks = new ArrayList<>();

    public <T extends OperationTask<?, ?>> void addOperationTask(T operationTask) {
        getOperationTasks().add(operationTask);
    }

```

## After adding H2 db, mvn package failed

But when running those TCs individually, they all pass.

```
[ERROR]   EmployeeControllerTest.getAllEmployeesAPI » IllegalState Failed to load Applic...
[ERROR]   GitHubUserControllerTest.getAllGithubUserAPI » IllegalState Failed to load App...
Caused by: org.h2.jdbc.JdbcSQLException: Table "xxx" already exists;
```

"If the tests are run individually, they pass. I think the problem is due to schema.sql being executed twice against the same database. It fails the second time as the tables already exist."

Solution, added *@AutoConfigureTestDatabase* on those test classes.

## Docker

### Building the Container Image

- See updating Dockerfile

```
docker build  -t mockbackend:v1 .

Make sure to include . at the end
Here, -t simply means tag followed by ' name:tag ' format.

albert@albert-mint20:~/Documents/sharing/github/springboot-dev-repo/spring-boot-mock-backend$ docker build  -t mockbackend:v1 .
Sending build context to Docker daemon  48.34MB
Step 1/4 : FROM adoptopenjdk:11-jre-hotspot
11-jre-hotspot: Pulling from library/adoptopenjdk
f3ef4ff62e0d: Pull complete 
706b9b9c1c44: Pull complete 
76205aac4d5a: Pull complete 
Digest: sha256:ad6431b2e2a6f8016aa6d79c3f588783af9fdc06cafe131fd0d3faf560914b13
Status: Downloaded newer image for adoptopenjdk:11-jre-hotspot
 ---> 2c57fb3bc67b
Step 2/4 : ARG JAR_FILE=target/*.jar
 ---> Running in 9e87dbb41f16
Removing intermediate container 9e87dbb41f16
 ---> af2b04805d18
Step 3/4 : COPY ${JAR_FILE} spring-boot-mock-backend-1.0-SNAPSHOT.jar
 ---> 5147a26a373f
Step 4/4 : ENTRYPOINT ["java","-jar","/spring-boot-mock-backend-1.0-SNAPSHOT.jar"]
 ---> Running in 733f2ecb3f65
Removing intermediate container 733f2ecb3f65
 ---> 8f7f4894b902
Successfully built 8f7f4894b902
Successfully tagged mockbackend:v1

```

### Run Docker image

docker run -d --name mockbackend -p 8888:8888 mockbackend:v1
docker run --name mockbackend -p 8888:8888 mockbackend:v1

### To see live logs you can run below command, or without -d

- Using docker log

```
docker logs -f mockbackend

docker logs -f <yourContainer> &> your.log &

Explanation:

-f (i.e.--follow): writes all existing logs and follows logging everything that comes next.
&> redirects both the standard output and standard error.
Likely you want to run that method in the background, thus the &.
You can separate stdout and stderr by: > output.log 2> error.log (instead of using &>).
```

- About docker log file
Docker by default store logs to one log file. To check log file path run command:

docker inspect --format='{{.LogPath}}' <containername>
docker inspect --format='{{.LogPath}}' mockbackend


tail -f `docker inspect --format='{{.LogPath}}' <containername>`
sudo tail -f `docker inspect --format='{{.LogPath}}' mockbackend`


### JsonUtil change to make sure file can be found in Docker container
```
new ClassPathResource(fileName).getInputStream()
```
