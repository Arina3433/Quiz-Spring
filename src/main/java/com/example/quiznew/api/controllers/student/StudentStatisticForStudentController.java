package com.example.quiznew.api.controllers.student;

import com.example.quiznew.api.dtos.student.StudentStatisticDto;
import com.example.quiznew.api.services.student.StudentStatisticForStudentService;
import jakarta.transaction.Transactional;
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
@RequestMapping("/quiz/app/student")
public class StudentStatisticForStudentController {

    StudentStatisticForStudentService studentStatisticForStudentService;

    static final String GET_STUDENT_STATISTIC = "/get-statistic";

    @Transactional
    @GetMapping(GET_STUDENT_STATISTIC)
    public ResponseEntity<List<StudentStatisticDto>> getStudentStatistic(
            @RequestParam(value = "quiz_id", required = false) Optional<String> optionalQuizId) {

        return ResponseEntity.ok(studentStatisticForStudentService.getStudentStatistic(optionalQuizId));
    }

}
