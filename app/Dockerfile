FROM openjdk:8-jdk-alpine

RUN apk add --no-cache bash

ADD . /usr/app/
WORKDIR /usr/app
RUN scripts/sbt assembly

EXPOSE 9000
ENTRYPOINT java -Dconfig.resource=production.conf -jar target/scala-2.13/app.jar
