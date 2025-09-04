# Step 1: Use a Gradle image to build the app
FROM gradle:8.2.1-jdk17 AS builder
WORKDIR /app

# Copy everything to /app
COPY . .

# Build the Spring Boot app
RUN gradle clean build --no-daemon

# Step 2: Use a smaller JDK runtime image for running the app
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

# Copy the JAR from the builder stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Expose port 8080
EXPOSE 8080

# Run the app
ENTRYPOINT ["java","-jar","app.jar"]
