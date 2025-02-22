FROM openjdk:21
COPY ./target/quiz-0.0.1-SNAPSHOT.jar /usr/src/quiz/
WORKDIR /usr/src/quiz/
EXPOSE 8080
CMD ["java", "-jar", "quiz-0.0.1-SNAPSHOT.jar"]