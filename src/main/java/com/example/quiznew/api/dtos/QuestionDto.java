package com.example.quiznew.api.dtos;

import com.example.quiznew.store.entities.Categories;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuestionDto {

    Long id;

    @JsonProperty("question_text")
    String questionText;

    @Enumerated(EnumType.STRING)
    Categories categories;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("answers")
    List<AnswerDto> answersList = new ArrayList<>();

}
