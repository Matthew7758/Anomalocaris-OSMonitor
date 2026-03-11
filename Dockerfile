# --- Stage 1: Build ---
FROM eclipse-temurin:21-jdk AS build
RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*
WORKDIR /app
COPY backend/pom.xml .
COPY backend/src ./src
RUN mvn clean package -DskipTests

# --- Stage 2: Runtime ---
# We use an Ubuntu-based image for better compatibility with nvidia-utils
FROM nvidia/cuda:12.0.0-runtime-ubuntu22.04
RUN apt-get update && apt-get install -y \
    openjdk-21-jre-headless \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

