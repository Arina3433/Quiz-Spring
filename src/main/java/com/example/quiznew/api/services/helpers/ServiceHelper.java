package com.example.quiznew.api.services.helpers;

import com.example.quiznew.api.exceptions.BadRequestException;
import com.example.quiznew.api.exceptions.NotFoundException;
import com.example.quiznew.store.entities.Answer;
import com.example.quiznew.store.entities.Categories;
import com.example.quiznew.store.entities.Question;
import com.example.quiznew.store.repositories.AnswerRepository;
import com.example.quiznew.store.repositories.QuestionRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Stream;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Component
public class ServiceHelper {

    QuestionRepository questionRepository;

    AnswerRepository answerRepository;

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
    public Optional<String> getStringOrEmptyAndCheckIfCategoryExistsOrElseThrow(
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

}
