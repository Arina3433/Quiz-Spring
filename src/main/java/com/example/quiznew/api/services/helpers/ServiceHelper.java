package com.example.quiznew.api.services.helpers;

import com.example.quiznew.api.exceptions.BadRequestException;
import com.example.quiznew.api.exceptions.NotFoundException;
import com.example.quiznew.store.entities.Answer;
import com.example.quiznew.store.entities.Categories;
import com.example.quiznew.store.entities.Question;
import com.example.quiznew.store.entities.Quiz;
import com.example.quiznew.store.repositories.AnswerRepository;
import com.example.quiznew.store.repositories.QuestionRepository;
import com.example.quiznew.store.repositories.QuizRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Component
public class ServiceHelper {

    QuestionRepository questionRepository;

    AnswerRepository answerRepository;

    QuizRepository quizRepository;

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
    public List<Question> getNeededQuestionsByIdOrThrow(List<Long> questionsId) {

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
    public Optional<String> checkIfCategoryExistsOrElseThrow(
            Optional<String> optionalQuestionCategory) {

        optionalQuestionCategory = optionalQuestionCategory.filter(questionCategory -> !questionCategory.isBlank());

        optionalQuestionCategory.ifPresent(questionCategory ->
                Stream.of(Categories.values())
                        .map(Categories::toString)
                        .filter(category -> category.equalsIgnoreCase(questionCategory))
                        .findAny()
                        .orElseThrow(() -> new BadRequestException(
                                        String.format(
                                                "The category of questions %s wasn't found.",
                                                questionCategory)
                                )
                        )
        );

        return optionalQuestionCategory;
    }

    public void findDuplicatesInRequestAndThrow(List<Long> questionsId) {
        List<String> duplicates = questionsId
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
                    String.format("You cannot add(delete) question(s) with id %s to(from) the quiz twice.",
                            String.join(", ", duplicates))
            );
        }
    }

}
