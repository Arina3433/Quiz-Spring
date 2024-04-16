package com.example.quiznew.api.services.student;

import com.example.quiznew.api.dtos.student.QuizToPassDto;
import com.example.quiznew.api.dtos.student.StudentResultDto;

import java.util.List;

public interface QuizPassService {

    QuizToPassDto getQuizToPass(Long quizId);

    StudentResultDto passQuizAndGetResults(Long quizId, List<Long> answersId);

}
