package com.example.quiznew.api.controllers.teacher;

import com.example.quiznew.api.dtos.student.StudentStatisticDto;
import com.example.quiznew.api.services.teacher.StudentStatisticForTeacherService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/quiz/app/teacher/statistic")
public class StudentStatisticController {

    StudentStatisticForTeacherService studentStatisticService;

    static final String GET_STUDENT_STATISTIC = "/get-statistic";

    @GetMapping(GET_STUDENT_STATISTIC)
    public ResponseEntity<List<StudentStatisticDto>> getStudentStatistic(
            @RequestParam(value = "student_name", required = false) Optional<String> optionalStudentName,
            @RequestParam(value = "quiz_id", required = false) Optional<String> optionalQuizId) {
        return ResponseEntity.ok(studentStatisticService.getStudentStatistic(optionalStudentName, optionalQuizId));
    }

}
