FROM maven:3.8.5 AS maven
LABEL MAINTAINER="kveskoir@gmail.com"
WORKDIR /usr/src/app
COPY . /usr/src/app
RUN mvn package

FROM openjdk:17-alpine
ARG JAR_FILE=receiver-0.0.1-SNAPSHOT.jar
WORKDIR /opt/app
COPY --from=maven /usr/src/app/receiver/target/${JAR_FILE} /opt/app/
ENTRYPOINT ["java","-jar","receiver-0.0.1-SNAPSHOT.jar"]