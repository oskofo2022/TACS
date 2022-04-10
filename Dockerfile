FROM tomcat:9-jdk8
ADD target/wordle.war /usr/local/tomcat/webapps/wordle.war
EXPOSE 8080
CMD ["catalina.sh", "run"]
