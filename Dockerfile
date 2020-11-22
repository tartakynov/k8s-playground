FROM openjdk:8-jre-alpine

COPY ./target/scala-2.13/app.jar /usr/app/
WORKDIR /usr/app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
