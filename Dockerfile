# Multi-stage Dockerfile for building the project with Maven and running the produced jar

# Build stage
FROM maven:3.9.4-eclipse-temurin-17 AS builder
WORKDIR /workspace

# Copy project files
COPY pom.xml ./
COPY .mvn .mvn
COPY src src

# Build using the Maven installation in the image and set a local repo location
RUN mvn -B -DskipTests -Dmaven.repo.local=/root/.m2/repository package

# Run stage
FROM eclipse-temurin:17-jdk
WORKDIR /app
ARG JAR_FILE=target/*.jar
COPY --from=builder /workspace/target/*.jar /app/app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
