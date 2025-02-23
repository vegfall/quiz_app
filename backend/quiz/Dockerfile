FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /usr/src/quiz
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:21
WORKDIR /usr/src/quiz
COPY --from=builder /usr/src/quiz/target/quiz-0.0.1-SNAPSHOT.jar /usr/src/quiz/
EXPOSE 8080
CMD ["java", "-jar", "quiz-0.0.1-SNAPSHOT.jar"]