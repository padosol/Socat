FROM openjdk:17

VOLUME /tmp

COPY apiEncryptionKey.jks apiEncryptionKey.jks

COPY build/libs/config-service-0.0.1-SNAPSHOT.jar ConfigServer.jar

ENTRYPOINT [ "java", "-jar", "-Dspring.profiles.active=dev", "ConfigServer.jar"]