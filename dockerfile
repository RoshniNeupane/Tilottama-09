# Use official OpenJDK 17 image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy Gradle wrapper and build files first (for caching)
COPY build.gradle settings.gradle ./
COPY gradlew .
COPY gradle ./gradle

# Copy source code
COPY src ./src

# Build the JAR inside the container
RUN ./gradlew clean bootJar --no-daemon

# Copy the generated JAR to a fixed name
RUN cp build/libs/*.jar app.jar

# Expose port (matches your application.properties server.port)
EXPOSE 8081

# Run the JAR
ENTRYPOINT ["java","-jar","app.jar"]
