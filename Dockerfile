FROM maven:3.8.5-openjdk-17 AS build

## Add the wait utility
ADD https://github.com/ufoscout/docker-compose-wait/releases/download/2.9.0/wait /wait
RUN chmod +x /wait

WORKDIR /usr/local/webapps

ARG JAR_FILE=target/*.war
COPY ${JAR_FILE} /wordle.war

COPY /start.sh ./scripts/start.sh
RUN ["chmod", "+x", "./scripts/start.sh"]

ENTRYPOINT ["./scripts/start.sh"]
EXPOSE 8080

