package com.example.quiznew.api.services;

import com.example.quiznew.api.dtos.QuizDtoRequestToCreate;
import com.example.quiznew.api.dtos.QuizDtoRequestToEdit;
import com.example.quiznew.api.dtos.QuizDtoResponse;

import java.util.List;

public interface QuizService {

    QuizDtoResponse createQuiz(QuizDtoRequestToCreate dto);

    QuizDtoResponse editQuizById(Long quizId, QuizDtoRequestToEdit dto);

    QuizDtoResponse getQuizById(Long quizId);

    List<QuizDtoResponse> getAllQuizzes();

    String deleteQuizById(Long quizId);

    String deleteAllQuizzes();

}
