FROM openjdk:8-jdk-alpine
EXPOSE 8080
COPY target/post-ws.jar post-ws.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=docker", "post-ws.jar"]
