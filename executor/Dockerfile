FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY target/executor-*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]