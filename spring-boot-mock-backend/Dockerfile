FROM java:8

# in application.properties, 29080
# EXPOSE 39080


ADD ./target/spring-boot-mock-backend-1.0-SNAPSHOT.jar spring-boot-mock-backend-1.0-SNAPSHOT.jar


#ENTRYPOINT ["java", "-jar", "/spring-boot-mock-backend-docker.jar"]
ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5005", "-jar", "/spring-boot-mock-backend-docker.jar"]

