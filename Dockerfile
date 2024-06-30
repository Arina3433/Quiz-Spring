FROM openjdk:23-ea-21-jdk-slim-bullseye
WORKDIR /quiz-app
COPY target/Quiz-new-0.0.1-SNAPSHOT.jar /quiz-app/Quiz-new.jar
ENTRYPOINT ["java", "-jar", "Quiz-new.jar"]