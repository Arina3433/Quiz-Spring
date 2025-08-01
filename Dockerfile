FROM openjdk:17-jdk-slim
WORKDIR /quiz-app
COPY target/Quiz-new-0.0.1-SNAPSHOT.jar /quiz-app/Quiz-new.jar
ENTRYPOINT ["java", "-jar", "Quiz-new.jar", "--spring.profiles.active=prod"]