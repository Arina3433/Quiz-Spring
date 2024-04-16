package com.example.quiznew.api.controllers.student;

import com.example.quiznew.api.dtos.student.QuizToPassDto;
import com.example.quiznew.api.dtos.student.StudentResultDto;
import com.example.quiznew.api.services.student.QuizPassService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/quiz/app/student")
public class PassQuizController {

    QuizPassService quizPassService;

    static final String GET_QUIZ_BY_ID_TO_PASS = "/get-quiz/{quiz_id}";
    static final String PASS_QUIZ_BY_ID_AND_GET_RESULTS = "/get-results/{quiz_id}";

    @Transactional
    @GetMapping(GET_QUIZ_BY_ID_TO_PASS)
    public ResponseEntity<QuizToPassDto> getQuizToPass(
            @PathVariable("quiz_id") Long quizId) {

        return ResponseEntity.ok(quizPassService.getQuizToPass(quizId));
    }

    @Transactional
    @PostMapping(PASS_QUIZ_BY_ID_AND_GET_RESULTS)
    public ResponseEntity<StudentResultDto> passQuizAndGetResults(
            @PathVariable("quiz_id") Long quizId,
            @RequestBody List<Long> answersId) {

        return ResponseEntity.ok(quizPassService.passQuizAndGetResults(quizId, answersId));

    }

}
