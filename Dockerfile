# Stage 1: Build JAR and run tests
# We use an image that already includes Maven and JDK 21
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# 1. Copy only the POM to cache dependencies
COPY pom.xml .
# 2. Download all dependencies
RUN mvn dependency:go-offline
# 3. Copy the remaining source code
COPY . .

# 4. Run Tests and Verify (The 'verify' phase runs all tests)
# -DforkCount=0: It is vital to stabilize test execution in the container.
# If tests fail (Failure) or produce a runtime error (Error), the build stops here.
RUN mvn clean verify -DforkCount=0

# Stage 2: Create light weight production image
# We use JRE Alpine for a small final image
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy the compiled JAR from the previous stage (Stage 1)
# The JAR is generated during the 'verify' phase
COPY --from=build /app/target/*.jar app.jar

# Expose port and execute
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
