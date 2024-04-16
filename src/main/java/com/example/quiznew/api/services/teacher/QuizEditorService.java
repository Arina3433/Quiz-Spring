package com.example.quiznew.api.services.teacher;

import com.example.quiznew.api.dtos.teacher.QuizEditorRequestToCreateDto;
import com.example.quiznew.api.dtos.teacher.QuizEditorRequestToEditDto;
import com.example.quiznew.api.dtos.teacher.QuizEditorResponseDto;

import java.util.List;

public interface QuizEditorService {

    QuizEditorResponseDto createQuiz(QuizEditorRequestToCreateDto dto);

    QuizEditorResponseDto editQuizById(Long quizId, QuizEditorRequestToEditDto dto);

    QuizEditorResponseDto getQuizById(Long quizId);

    List<QuizEditorResponseDto> getAllQuizzes();

    String deleteQuizById(Long quizId);

    String deleteAllQuizzes();

}
