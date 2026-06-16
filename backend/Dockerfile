# Backend Dockerfile
FROM maven:3.9.4-eclipse-temurin-21 AS builder

WORKDIR /build
COPY backend/pom.xml .
COPY backend/src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre

WORKDIR /app
COPY --from=builder /build/target/school-meal-management-1.0.0.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
