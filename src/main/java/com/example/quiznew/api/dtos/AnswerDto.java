package com.example.quiznew.api.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AnswerDto {

    Long id;

    @JsonProperty("answers_text")
    String answerText;

    @JsonProperty("is_correct")
    Boolean isCorrect = false;

}
