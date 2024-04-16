package com.example.quiznew.api.services.teacher;

import com.example.quiznew.api.dtos.teacher.AnswerEditorDto;

import java.util.Optional;

public interface AnswerEditorService {

    AnswerEditorDto createAnswer(Long questionId, String answerText, Optional<Boolean> optionalIsCorrect);

    AnswerEditorDto editAnswerById(Long answerId, Optional<String> optionalAnswerText, Optional<Boolean> optionalIsCorrect);

    String deleteAnswerById(Long answerId);

    String deleteAllAnswersByQuestionId(Long questionId);

}
