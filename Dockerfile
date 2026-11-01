# Build stage
FROM eclipse-temurin:21-jdk AS build

WORKDIR /app

# Copy Maven files
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./

# Give Maven wrapper permission and download dependencies
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline

# Copy project source code
COPY src ./src

# Build the Spring Boot application
RUN ./mvnw clean package -DskipTests


# Run stage
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy the built JAR
COPY --from=build /app/target/*.jar app.jar

# Render provides the PORT environment variable
EXPOSE 8080

# Start the application
ENTRYPOINT ["sh", "-c", "java -Dserver.port=${PORT:-8080} -jar app.jar"]