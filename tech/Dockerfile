# Etapa de build
FROM maven:3.9.9-amazoncorretto-21-debian AS build

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests


RUN ls -la /app/target


FROM openjdk:21-jdk

WORKDIR /app


COPY --from=build /app/target/tech-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]