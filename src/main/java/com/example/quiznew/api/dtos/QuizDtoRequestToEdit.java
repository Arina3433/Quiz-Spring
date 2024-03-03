package com.example.quiznew.api.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuizDtoRequestToEdit {

    Long id;

    @JsonProperty("quiz_name")
    String optionalQuizName;

    @JsonProperty("questions_to_add_id's")
    List<Long> optionalQuestionsToAddId;

    @JsonProperty("questions_to_delete_id's")
    List<Long> optionalQuestionsToDeleteId;

}
