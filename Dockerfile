FROM maven:3.9.6-eclipse-temurin-21-alpine AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn install -DskipTests

FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY --from=build /app/target/flight_app-1.0.0-SNAPSHOT.jar .

ENTRYPOINT ["java", "-jar", "flight_app-1.0.0-SNAPSHOT.jar"]