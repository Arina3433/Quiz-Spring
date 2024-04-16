package com.example.quiznew.api.dtos.student;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AnswerToPassQuizDto {

    Long id;

    @JsonProperty("answers_text")
    String answerText;

}
