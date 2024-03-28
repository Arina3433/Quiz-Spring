package com.example.quiznew.api.dtos;

import com.example.quiznew.store.entities.QuestionCategories;
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
    @JsonProperty("question_category")
    QuestionCategories questionCategories;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("answers")
    List<AnswerDto> answersList = new ArrayList<>();

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("quizzes_id")
    List<Long> quizzesIdList = new ArrayList<>();

}
