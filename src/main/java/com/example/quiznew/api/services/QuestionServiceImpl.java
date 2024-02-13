package com.example.quiznew.api.services;

import com.example.quiznew.api.converters.QuestionConverter;
import com.example.quiznew.api.dtos.QuestionDto;
import com.example.quiznew.api.exceptions.BadRequestException;
import com.example.quiznew.api.services.helpers.ServiceHelper;
import com.example.quiznew.store.entities.Question;
import com.example.quiznew.store.repositories.QuestionRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class QuestionServiceImpl implements QuestionService {

    final QuestionRepository questionRepository;

    final QuestionConverter questionConverter;

    ServiceHelper serviceHelper;

    @Transactional
    @Override
    public QuestionDto createQuestion(String questionText) {

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

        Question question = questionRepository.saveAndFlush(
                Question.builder()
                        .questionText(questionText)
                        .build()
        );

        return questionConverter.convertToQuestionDto(question);
    }

// Как лучше через сеттер или через билдер?

    @Transactional
    @Override
    public QuestionDto editQuestionById(Long questionId, String questionText) {

        if (questionText.isBlank()) {
            throw new BadRequestException("Question text can't be empty");
        }

        Question question = serviceHelper.getQuestionByIdOrElseThrow(questionId);

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

        question = questionRepository.saveAndFlush(question);

        return questionConverter.convertToQuestionDto(question);
    }

    @Transactional
    @Override
    public QuestionDto getQuestionById(Long questionId) {

        Question question = serviceHelper.getQuestionByIdOrElseThrow(questionId);

        return questionConverter.convertToQuestionDto(question);
    }

    @Transactional
    @Override
    public List<QuestionDto> getAllQuestions() {

        List<Question> questionList = questionRepository.findAllBy();

        return questionConverter.convertToQuestionDtoList(questionList);
    }

    @Transactional
    @Override
    public String deleteQuestionById(Long questionId) {

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
