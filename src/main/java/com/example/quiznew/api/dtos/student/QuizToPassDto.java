package com.example.quiznew.api.dtos.student;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuizToPassDto {

    Long id;

    @JsonProperty("quiz_name")
    String quizName;

    @JsonProperty("questions_in_quiz")
    List<QuestionToPassQuizDto> questionsInQuizList = new ArrayList<>();

}
