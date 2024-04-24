package com.example.quiznew.api.dtos.student;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentStatisticDto {

    Long id;

    @JsonProperty("student_name")
    String studentName;

    @JsonProperty("quiz_id")
    Long quizId;

    @JsonProperty("student_result")
    String studentResult;

    @JsonProperty("date_of_completion_quiz")
    Date dateOfCompletionQuiz;

}
