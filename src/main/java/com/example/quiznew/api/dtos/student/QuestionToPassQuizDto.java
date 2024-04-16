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
public class QuestionToPassQuizDto {

    Long id;

    @JsonProperty("question_text")
    String questionText;

    @JsonProperty("answers")
    List<AnswerToPassQuizDto> answersList = new ArrayList<>();

}
