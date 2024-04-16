package com.example.quiznew.api.dtos.teacher;

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
public class QuizEditorRequestToCreateDto {

    Long id;

    @JsonProperty("quiz_name")
    String quizName;

    @JsonProperty("questions_to_add_id's")
    List<Long> questionsToAddId = new ArrayList<>();

}
