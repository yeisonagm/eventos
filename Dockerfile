FROM openjdk:21-jdk-slim
ARG JAR_FILE=target/eventos-0.0.1.jar
COPY ${JAR_FILE} eventos.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "eventos.jar"]