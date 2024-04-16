package com.example.quiznew.api.services.teacher;

import com.example.quiznew.api.dtos.teacher.QuestionEditorDto;

import java.util.List;
import java.util.Optional;

public interface QuestionEditorService {

    QuestionEditorDto createQuestion(String questionText, Optional<String> optionalQuestionCategory);

    QuestionEditorDto editQuestionById(Long questionId,
                                       Optional<String> optionalQuestionText,
                                       Optional<String> optionalQuestionCategory);

    QuestionEditorDto getQuestionById(Long questionId);

    List<QuestionEditorDto> getAllQuestions(Optional<String> optionalQuestionCategory);

    String deleteQuestionById(Long questionId);

    String deleteAllQuestions();

}
