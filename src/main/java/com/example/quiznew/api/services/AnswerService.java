package com.example.quiznew.api.services;

import com.example.quiznew.api.dtos.AnswerDto;

import java.util.Optional;

public interface AnswerService {

    AnswerDto createAnswer(Long questionId, String answerText, Optional<Boolean> optionalIsCorrect);

    AnswerDto editAnswerById(Long answerId, Optional<String> optionalAnswerText, Optional<Boolean> optionalIsCorrect);

    String deleteAnswerById(Long answerId);

    String deleteAllAnswersByQuestionId(Long questionId);

}
