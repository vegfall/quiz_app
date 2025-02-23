FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /usr/src/question
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:21
WORKDIR /usr/src/question
COPY --from=builder /usr/src/question/target/question-0.0.1-SNAPSHOT.jar /usr/src/question/
EXPOSE 8081
CMD ["java", "-jar", "question-0.0.1-SNAPSHOT.jar"]