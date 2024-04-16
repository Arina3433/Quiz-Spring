package com.example.quiznew.api.services.teacher.implementation;

import com.example.quiznew.api.converters.teacher.AnswerEditorConverter;
import com.example.quiznew.api.dtos.teacher.AnswerEditorDto;
import com.example.quiznew.api.exceptions.BadRequestException;
import com.example.quiznew.api.services.teacher.AnswerEditorService;
import com.example.quiznew.api.services.helper.ServiceHelper;
import com.example.quiznew.store.entities.Answer;
import com.example.quiznew.store.entities.Question;
import com.example.quiznew.store.repositories.AnswerRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AnswerEditorServiceImpl implements AnswerEditorService {

    AnswerRepository answerRepository;

    AnswerEditorConverter answerConverter;

    ServiceHelper serviceHelper;

    @Override
    @Transactional
    public AnswerEditorDto createAnswer(Long questionId,
                                        String answerText,
                                        Optional<Boolean> optionalIsCorrect) {

        if (answerText.trim().isEmpty()) {
            throw new BadRequestException("Answer text can't be empty");
        }

        Question question = serviceHelper.getQuestionByIdOrElseThrow(questionId);

        question
                .getAnswersList()
                .stream()
                .map(Answer::getAnswerText)
                .filter(anotherAnswerText -> anotherAnswerText.equalsIgnoreCase(answerText))
                .findAny()
                .ifPresent(anotherAnswer -> {
                            throw new BadRequestException(String.format(
                                    "Answer %s already exist.", answerText));
                        }
                );

        Answer answer = answerRepository.saveAndFlush(
                Answer.builder()
                        .answerText(answerText)
                        .isCorrect(optionalIsCorrect.orElse(false))
                        .question(question)
                        .build()
        );

        return answerConverter.convertToAnswerDto(answer);
    }

    @Override
    @Transactional
    public AnswerEditorDto editAnswerById(Long answerId,
                                          Optional<String> optionalAnswerText,
                                          Optional<Boolean> optionalIsCorrect) {

        Answer answer = serviceHelper.getAnswerByIdOrElseThrow(answerId);

        optionalAnswerText = optionalAnswerText.filter(optional -> !optional.isBlank());

        optionalAnswerText.ifPresent(answerText -> {
                    answerRepository.findByAnswerText(answerText)
                            .filter(anotherAnswer -> !Objects.equals(anotherAnswer.getId(), answerId))
                            .ifPresent(anotherAnswer -> {
                                throw new BadRequestException(
                                        String.format(
                                                "Answer with the same text already exists with id %s.",
                                                anotherAnswer.getId()
                                        )
                                );
                            });

                    answer.setAnswerText(answerText);
                }
        );

        optionalIsCorrect.ifPresent(answer::setIsCorrect);

        final Answer editedAnswer = answerRepository.saveAndFlush(answer);

        return answerConverter.convertToAnswerDto(editedAnswer);
    }

    @Override
    @Transactional
    public String deleteAnswerById(Long answerId) {

        serviceHelper.getAnswerByIdOrElseThrow(answerId);

        answerRepository.deleteById(answerId);

        return String.format("Answer with id %s has been deleted.", answerId);
    }

    @Override
    @Transactional
    public String deleteAllAnswersByQuestionId(Long questionId) {

        answerRepository.deleteAllByQuestionId(questionId);

        return String.format("All answers of question with id %s has been deleted.", questionId);
    }

}
