# Build stage
FROM maven:3.9.5-eclipse-temurin-21 AS build
WORKDIR /app

COPY . .
RUN mvn clean package -DskipTests

# Run stage
FROM openjdk:21-jdk-slim
WORKDIR /app

# Copy the WAR file from the build stage
COPY --from=build /app/target/websiteSellingLaptop-0.0.1-SNAPSHOT.war websiteSellingLaptop.war

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "websiteSellingLaptop.war"]
