package com.example.quiznew.api.services.implementation;

import com.example.quiznew.api.converters.QuestionConverter;
import com.example.quiznew.api.dtos.QuestionDto;
import com.example.quiznew.api.exceptions.BadRequestException;
import com.example.quiznew.api.exceptions.NotFoundException;
import com.example.quiznew.api.services.QuestionService;
import com.example.quiznew.api.services.helpers.ServiceHelper;
import com.example.quiznew.store.entities.Categories;
import com.example.quiznew.store.entities.Question;
import com.example.quiznew.store.repositories.QuestionRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class QuestionServiceImpl implements QuestionService {

    final QuestionRepository questionRepository;

    final QuestionConverter questionConverter;

    ServiceHelper serviceHelper;

    @Transactional
    @Override
    public QuestionDto createQuestion(String questionText,
                                      Optional<String> optionalQuestionCategory) {

        if (questionText.isBlank()) {
            throw new BadRequestException("Question text can't be empty");
        }

        questionRepository
                .findByQuestionText(questionText)
                .ifPresent(anotherQuestion -> {
                    throw new BadRequestException(
                            String.format(
                                    "Question with the same text already exists with id %s.",
                                    anotherQuestion.getId()
                            )
                    );
                });

        optionalQuestionCategory = serviceHelper
                .checkIfCategoryExistsOrElseThrow(optionalQuestionCategory);


        Question question = questionRepository.saveAndFlush(
                Question.builder()
                        .questionText(questionText)
                        .categories(Categories.valueOf(optionalQuestionCategory.orElse("common").toUpperCase()))
                        .build()
        );

        return questionConverter.convertToQuestionDto(question);
    }

    @Transactional
    @Override
    public QuestionDto editQuestionById(Long questionId,
                                        Optional<String> optionalQuestionText,
                                        Optional<String> optionalQuestionCategory) {

        if (optionalQuestionText.isEmpty() && optionalQuestionCategory.isEmpty()) {
            throw new BadRequestException("Parameters for the change are not set");
        }

        Question question = serviceHelper.getQuestionByIdOrElseThrow(questionId);

        optionalQuestionText = optionalQuestionText.filter(questionText -> !questionText.isBlank());

        optionalQuestionText.ifPresent(
                questionText -> {
                    questionRepository
                            .findByQuestionText(questionText)
                            .filter(anotherQuestion -> !Objects.equals(anotherQuestion.getId(), questionId))
                            .ifPresent(anotherQuestion -> {
                                throw new BadRequestException(
                                        String.format(
                                                "Question with the same text already exists with id %s.",
                                                anotherQuestion.getId()
                                        )
                                );
                            });

                    question.setQuestionText(questionText);
                }
        );


        optionalQuestionCategory = serviceHelper
                .checkIfCategoryExistsOrElseThrow(optionalQuestionCategory);

        optionalQuestionCategory.ifPresent(questionCategory ->
                question.setCategories(Categories.valueOf(questionCategory.toUpperCase()))
        );

        final Question editedQuestion = questionRepository.saveAndFlush(question);

        return questionConverter.convertToQuestionDto(editedQuestion);
    }

    @Transactional
    @Override
    public QuestionDto getQuestionById(Long questionId) {

        Question question = serviceHelper.getQuestionByIdOrElseThrow(questionId);

        return questionConverter.convertToQuestionDto(question);
    }

    @Transactional
    @Override
    public List<QuestionDto> getAllQuestions(Optional<String> optionalQuestionCategory) {

        optionalQuestionCategory = serviceHelper
                .checkIfCategoryExistsOrElseThrow(optionalQuestionCategory);

        List<Question> questionList = optionalQuestionCategory
                .map(questionCategory ->
                        questionRepository
                                .findAllByCategories(Categories.valueOf(questionCategory.toUpperCase()))
                                .filter(list -> !list.isEmpty())
                                .orElseThrow(() -> new NotFoundException(
                                                String.format(
                                                        "Questions from the category %s weren't found.",
                                                        questionCategory
                                                )
                                        )
                                )
                )
                .orElseGet(() -> {
                    List<Question> allQuestions = questionRepository.findAll();
                    if (allQuestions.isEmpty()) {
                        throw new NotFoundException("No questions found.");
                    }
                    return allQuestions;
                });

        return questionConverter.convertToQuestionDtoList(questionList);
    }

    @Transactional
    @Override
    public String deleteQuestionById(Long questionId) {

        serviceHelper.getQuestionByIdOrElseThrow(questionId);

        questionRepository.deleteById(questionId);

        return String.format("Question with id %s has been deleted.", questionId);
    }

    @Transactional
    @Override
    public String deleteAllQuestions() {

        questionRepository.deleteAll();

        return "All is deleted";
    }

}
