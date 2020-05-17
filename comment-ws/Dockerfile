FROM openjdk:8-jdk-alpine
EXPOSE 8081
COPY target/comment-ws.jar comment-ws.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=docker", "comment-ws.jar"]
