package com.example.quiznew.api.dtos.teacher;

import com.fasterxml.jackson.annotation.JsonInclude;
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
public class QuizEditorResponseDto {

    Long id;

    @JsonProperty("quiz_name")
    String quizName;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("questions_in_quiz")
    List<QuestionEditorDto> questionsInQuizList = new ArrayList<>();

}
