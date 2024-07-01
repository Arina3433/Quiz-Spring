package com.example.quiznew.api.services.teacher.implementation;

import com.example.quiznew.api.converters.teacher.QuestionEditorConverter;
import com.example.quiznew.api.dtos.teacher.QuestionEditorDto;
import com.example.quiznew.api.exceptions.BadRequestException;
import com.example.quiznew.api.exceptions.NotFoundException;
import com.example.quiznew.api.services.teacher.QuestionEditorService;
import com.example.quiznew.api.services.helper.ServiceHelper;
import com.example.quiznew.store.entities.QuestionCategories;
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
public class QuestionEditorServiceImpl implements QuestionEditorService {

    QuestionRepository questionRepository;

    QuestionEditorConverter questionConverter;

    ServiceHelper serviceHelper;

    @Transactional
    @Override
    public QuestionEditorDto createQuestion(String questionText,
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

        optionalQuestionCategory = optionalQuestionCategory.filter(category -> !category.isBlank());

        Question question = questionRepository.saveAndFlush(
                Question.builder()
                        .questionText(questionText)
                        .questionCategories(QuestionCategories
                                .toQuestionCategoryFromString(optionalQuestionCategory.orElse(null))
                        )
                        .build()
        );

        return questionConverter.convertToQuestionDto(question);
    }

    @Transactional
    @Override
    public QuestionEditorDto editQuestionById(Long questionId,
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

        optionalQuestionCategory = optionalQuestionCategory.filter(category -> !category.isBlank());

        optionalQuestionCategory.ifPresent(questionCategory ->
                question.setQuestionCategories(QuestionCategories.toQuestionCategoryFromString(questionCategory))
        );

        final Question editedQuestion = questionRepository.saveAndFlush(question);

        return questionConverter.convertToQuestionDto(editedQuestion);
    }

    @Transactional
    @Override
    public QuestionEditorDto getQuestionById(Long questionId) {

        Question question = serviceHelper.getQuestionByIdOrElseThrow(questionId);

        return questionConverter.convertToQuestionDto(question);
    }

    @Transactional
    @Override
    public List<QuestionEditorDto> getAllQuestions(Optional<String> optionalQuestionCategory) {

        optionalQuestionCategory = optionalQuestionCategory.filter(category -> !category.isBlank());

        List<Question> questionList = optionalQuestionCategory
                .map(questionCategory ->
                        questionRepository
                                .findAllByQuestionCategories(
                                        QuestionCategories.toQuestionCategoryFromString(questionCategory)
                                )
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

        return "All questions have been deleted";
    }

}
