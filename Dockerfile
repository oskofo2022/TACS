FROM maven:3.8.5-openjdk-17 AS build
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /wordle.jar
ENTRYPOINT ["java","-jar","/wordle.jar"]
EXPOSE 8080

