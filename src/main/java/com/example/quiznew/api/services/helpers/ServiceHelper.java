package com.example.quiznew.api.services.helpers;

import com.example.quiznew.api.exceptions.NotFoundException;
import com.example.quiznew.store.entities.Answer;
import com.example.quiznew.store.entities.Question;
import com.example.quiznew.store.repositories.AnswerRepository;
import com.example.quiznew.store.repositories.QuestionRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

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

}
