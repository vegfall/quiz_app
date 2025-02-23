FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /usr/src/result
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:21
WORKDIR /usr/src/result
COPY --from=builder /usr/src/result/target/result-0.0.1-SNAPSHOT.jar /usr/src/result/
EXPOSE 8082
CMD ["java", "-jar", "result-0.0.1-SNAPSHOT.jar"]