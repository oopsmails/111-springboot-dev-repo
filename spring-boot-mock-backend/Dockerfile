FROM java:8

# in application.properties, 29080
# EXPOSE 39080


ADD ./target/springboot2-app-all-backendmock-1.0-SNAPSHOT.jar springboot2-app-all-backendmock-docker.jar


#ENTRYPOINT ["java", "-jar", "/springboot2-app-all-backendmock-docker.jar"]
ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5005", "-jar", "/springboot2-app-all-backendmock-docker.jar"]

