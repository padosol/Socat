FROM openjdk:17

VOLUME /tmp

COPY build/libs/room-server-0.0.1-SNAPSHOT.jar RoomService.jar

ENTRYPOINT [ "java", "-jar", "RoomService.jar"]