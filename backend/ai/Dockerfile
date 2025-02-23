FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /usr/src/ai
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:21
WORKDIR /usr/src/ai
COPY --from=builder /usr/src/ai/target/ai-0.0.1-SNAPSHOT.jar /usr/src/ai/
EXPOSE 8083
CMD ["java", "-jar", "ai-0.0.1-SNAPSHOT.jar"]