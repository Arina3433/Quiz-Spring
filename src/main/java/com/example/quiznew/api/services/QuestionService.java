package com.example.quiznew.api.services;

import com.example.quiznew.api.dtos.QuestionDto;

import java.util.List;

public interface QuestionService {

    QuestionDto createQuestion(String questionText);

    QuestionDto editQuestionById(Long questionId, String questionText);

    QuestionDto getQuestionById(Long questionId);

    List<QuestionDto> getAllQuestions();

    String deleteQuestionById(Long questionId);

    String deleteAllQuestions();

}
