package com.example.quiznew.api.services;

import com.example.quiznew.api.dtos.QuestionDto;

import java.util.List;
import java.util.Optional;

public interface QuestionService {

    QuestionDto createQuestion(String questionText, Optional<String> optionalQuestionCategory);

    QuestionDto editQuestionById(Long questionId,
                                 Optional<String> optionalQuestionText,
                                 Optional<String> optionalQuestionCategory);

    QuestionDto getQuestionById(Long questionId);

    List<QuestionDto> getAllQuestions(Optional<String> optionalQuestionCategory);

    String deleteQuestionById(Long questionId);

    String deleteAllQuestions();

}
