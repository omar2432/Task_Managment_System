# Stage 1: Build the application
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

# Stage 2: Run the application
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the built JAR file from the builder stage to the runtime stage
COPY --from=builder /app/target/*.jar app.jar

# Document the port (it's mainly for clarity)
EXPOSE 8084

# Command to run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]