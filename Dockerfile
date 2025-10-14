# Stage 1: build jar
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Copy files of Maven
COPY . .

# Build the jar
RUN ./mvnw clean package -DskipTests

# Stage 2: create light weight image
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# Copy the compiled jar from previous stage
COPY --from=build /app/target/*.jar app.jar

# Expose port and execute
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
