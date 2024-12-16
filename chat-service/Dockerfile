FROM openjdk:17-alpine

VOLUME /tmp

COPY build/libs/chat-service-0.0.1-SNAPSHOT.jar ChatService.jar

ENTRYPOINT [ "java", "-jar", "ChatService.jar"]