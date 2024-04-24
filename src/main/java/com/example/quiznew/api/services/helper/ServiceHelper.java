package com.example.quiznew.api.services.helper;

import com.example.quiznew.api.exceptions.BadRequestException;
import com.example.quiznew.api.exceptions.NotFoundException;
import com.example.quiznew.store.entities.*;
import com.example.quiznew.store.repositories.*;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Component
public class ServiceHelper {

    QuestionRepository questionRepository;

    AnswerRepository answerRepository;

    QuizRepository quizRepository;

    StudentStatisticRepository studentStatisticRepository;

    UserRepository userRepository;

    @Transactional
    public Question getQuestionByIdOrElseThrow(Long questionId) {
        return questionRepository.
                findById(questionId)
                .orElseThrow(() -> new NotFoundException(
                                String.format("Question with id %s doesn't exists.", questionId)
                        )
                );
    }

    @Transactional
    public Answer getAnswerByIdOrElseThrow(Long answerId) {
        return answerRepository.
                findById(answerId)
                .orElseThrow(() -> new NotFoundException(
                                String.format("Answer with id %s doesn't exists.", answerId)
                        )
                );
    }

    @Transactional
    public Quiz getQuizByIdOrElseThrow(Long quizId) {
        return quizRepository.
                findById(quizId)
                .orElseThrow(() -> new NotFoundException(
                                String.format("Quiz with id %s doesn't exists.", quizId)
                        )
                );
    }

    @Transactional
    public User getUserByUsernameOrElseThrow(String username) {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new BadRequestException(
                                String.format("User with username %s was not found.", username)
                        )
                );
    }

    @Transactional
    public List<StudentStatistic> getStudentStatisticsOrElseThrow() {

        List<StudentStatistic> studentStatisticList = studentStatisticRepository.findAll();

        if (studentStatisticList.isEmpty()) {
            throw new NotFoundException("Statistics were not found.");
        }

        return studentStatisticList;
    }

    @Transactional
    public List<StudentStatistic> getStudentStatisticsByStudentNameOrElseThrow(String studentName) {

        return studentStatisticRepository
                .findStudentStatisticByStudentName(studentName)
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Statistics for %s were not found.", studentName))
                );
    }

    public List<StudentStatistic> getStudentStatisticsByQuizIdOrElseThrow(Long quizId) {

        return studentStatisticRepository
                .findStudentStatisticByQuizId(quizId)
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Statistics on the quiz with id %s were not found.", quizId))
                );
    }

    @Transactional
    public List<StudentStatistic> getStudentStatisticsByStudentNameAndQuizIdOrElseThrow(
            String studentName, Long quizId) {

        return studentStatisticRepository
                .findStudentStatisticByStudentNameAndQuizId(studentName, quizId)
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new NotFoundException(
                                String.format("Statistics on the quiz with id %s were not found.", quizId)
                        )
                );
    }

    @Transactional
    public List<Question> getNeededQuestionsByIdOrElseThrow(List<Long> questionsId) {

        List<Question> foundQuestions = questionRepository.findAllById(questionsId);

        if (foundQuestions.size() != questionsId.size()) {
            List<String> notFoundQuestionsId = questionsId
                    .stream()
                    .filter(id ->
                            foundQuestions
                                    .stream()
                                    .noneMatch(question -> question.getId().equals(id))
                    )
                    .map(Object::toString)
                    .collect(Collectors.toList());

            throw new NotFoundException(
                    String.format("Question with id %s doesn't exists.",
                            String.join(", ", notFoundQuestionsId))
            );
        }

        return foundQuestions;
    }

    @Transactional
    public List<Answer> getNeededAnswersByIdOrElseThrow(List<Long> answersId) {

        List<Answer> foundAnswers = answerRepository.findAllById(answersId);

        if (foundAnswers.size() != answersId.size()) {
            List<String> notFoundAnswersId = answersId
                    .stream()
                    .filter(id ->
                            foundAnswers
                                    .stream()
                                    .noneMatch(answer -> answer.getId().equals(id))
                    )
                    .map(Object::toString)
                    .collect(Collectors.toList());

            throw new NotFoundException(
                    String.format("Answer with id %s doesn't exists.",
                            String.join(", ", notFoundAnswersId))
            );
        }

        return foundAnswers;
    }

    @Transactional
    public void findDuplicatesInRequestAndThrow(List<Long> ids) {
        List<String> duplicates = ids
                .stream()
                //группируем в map (id -> количество повторений)
                .collect(Collectors.groupingBy(Function.identity()))
                //проходим по группам
                .entrySet()
                .stream()
                //отбираем id, встречающиеся более одного раза
                .filter(e -> e.getValue().size() > 1)
                //вытаскиваем ключи
                .map(Map.Entry::getKey)
                .map(Objects::toString)
                .collect(Collectors.toList());

        if (!duplicates.isEmpty()) {
            throw new BadRequestException(
                    String.format("You cannot use records with id %s in your request more than once.",
                            String.join(", ", duplicates))
            );
        }
    }

    @Transactional
    public void checkIfThereAreAnswersInQuizOrElseThrow(List<Long> answersId, Quiz quiz) {
        Set<Long> answersToQuestionsInQuizIdsSet =
                quiz
                        .getQuestionsInQuizList()
                        .stream()
                        .flatMap(el -> el.getAnswersList()
                                .stream()
                                .map(Answer::getId))
                        .collect(Collectors.toSet());

        List<Long> answersToUnacceptableQuestions = answersId.stream()
                .filter(id -> !answersToQuestionsInQuizIdsSet.contains(id)).toList();

        if (!answersToUnacceptableQuestions.isEmpty()) {

            throw new BadRequestException(
                    String.format("The following answers are not present in the quiz: %s.",
                            answersToUnacceptableQuestions.stream()
                                    .map(Object::toString)
                                    .collect(Collectors.joining(", "))
                    )
            );
        }
    }

    public Long getaLong(Optional<String> optionalQuizId) {
        Long quizId = null;

        if (optionalQuizId.filter(str -> !str.isBlank()).isPresent()) {
            quizId = optionalQuizId
                    .map(Long::parseLong)
                    .orElseThrow(() -> new BadRequestException("Incorrect entry of the quiz id."));
        }

        return quizId;
    }

}
