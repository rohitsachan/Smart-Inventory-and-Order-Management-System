FROM openjdk:17-slim

WORKDIR /app

# Copy the JAR file
COPY target/*.jar app.jar

# Create volume for logs
VOLUME /app/logs

# Expose ports
EXPOSE 8080

# Set environment variables
ENV SPRING_PROFILES_ACTIVE=prod

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]