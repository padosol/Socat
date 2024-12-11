FROM openjdk:17-alpine

VOLUME /tmp

COPY build/libs/apigateway-service-0.0.1-SNAPSHOT.jar ApigatewayService.jar

ENTRYPOINT [ "java", "-jar", "ApigatewayService.jar"]