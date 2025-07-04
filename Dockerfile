FROM maven:3.9-amazoncorretto-17 AS build
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src src
RUN mvn clean install

FROM eclipse-temurin:17-jdk-focal
WORKDIR /app

COPY --from=build /app/target/*.jar warehouse.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/warehouse.jar"]

