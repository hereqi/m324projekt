# Multi-stage build für Spring Boot Backend

# Stage 1: Build
FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

# Maven Dependencies kopieren (für Caching)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Source Code kopieren
COPY src ./src

# Build durchführen
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# JAR von Build-Stage kopieren
COPY --from=build /app/target/*.jar app.jar

# Healthcheck
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]

