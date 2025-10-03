# Step 1: Use a base image with Java
FROM openjdk:24-jdk-slim

# Step 2: Set working directory
WORKDIR /app

# Step 3: Copy the jar file into the container
COPY target/pasteBin-0.0.1-SNAPSHOT.jar app.jar

# Step 4: Run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]
