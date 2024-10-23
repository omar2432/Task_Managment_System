# Use a base image with Java
FROM openjdk:17-jdk-slim AS builder

# Set the working directory
WORKDIR /app

# Copy Maven wrapper and related files
COPY mvnw ./
COPY .mvn .mvn
COPY pom.xml ./

# Copy the rest of the application code
COPY src ./src

# Make mvnw executable
RUN chmod +x ./mvnw

# Build the application
RUN ./mvnw clean package -DskipTests

# Use a smaller base image for the final image
FROM openjdk:17-slim

# Set the working directory
WORKDIR /app

# Copy the built JAR file from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Doesn't actually publish the port (it's mainly for documentation)
EXPOSE 8082

# Command to run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
