FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /usr/src/gateway
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:21
WORKDIR /usr/src/gateway
COPY --from=builder /usr/src/gateway/target/gateway-0.0.1-SNAPSHOT.jar /usr/src/gateway/
EXPOSE 8000
CMD ["java", "-jar", "gateway-0.0.1-SNAPSHOT.jar"]