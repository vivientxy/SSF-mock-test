FROM maven:3-eclipse-temurin-21

WORKDIR /app

COPY src src
COPY pom.xml .
COPY mvnw.cmd .
COPY mvnw .
COPY .mvn .mvn

RUN mvn package -Dmaven.test.skip=true

ENV PORT=3000

EXPOSE ${PORT}

ENTRYPOINT SERVER_PORT=${PORT} java -jar target/practice-test-0.0.1-SNAPSHOT.jar