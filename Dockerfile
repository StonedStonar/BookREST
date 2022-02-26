FROM amazoncorretto:17.0.2

COPY target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]