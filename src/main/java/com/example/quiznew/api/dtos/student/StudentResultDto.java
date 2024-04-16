package com.example.quiznew.api.dtos.student;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentResultDto {

    @JsonProperty("student_name")
    String studentName;

    @JsonProperty("quiz_name")
    String quizName;

    @JsonProperty("student_result")
    String studentResult;

}
