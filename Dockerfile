FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /usr/local/webapps
ARG JAR_FILE=target/*.war
COPY ${JAR_FILE} /wordle.war
ENTRYPOINT ["java","-jar","/wordle.war"]
EXPOSE 8080

