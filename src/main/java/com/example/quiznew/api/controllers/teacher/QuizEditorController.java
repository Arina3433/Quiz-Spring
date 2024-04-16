package com.example.quiznew.api.controllers.teacher;

import com.example.quiznew.api.dtos.teacher.QuizEditorRequestToCreateDto;
import com.example.quiznew.api.dtos.teacher.QuizEditorRequestToEditDto;
import com.example.quiznew.api.dtos.teacher.QuizEditorResponseDto;
import com.example.quiznew.api.services.teacher.implementation.QuizEditorServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/quiz/app/teacher/quizzes")
public class QuizEditorController {

    QuizEditorServiceImpl quizService;

    static final String CREATE_QUIZ = "/create";
    static final String EDIT_QUIZ_BY_ID = "/edit/{quiz_id}";
    static final String GET_QUIZ_BY_ID = "/get/{quiz_id}";
    static final String GET_ALL_QUIZZES = "/get_all";
    static final String DELETE_QUIZ_BY_ID = "/delete/{quiz_id}";
    static final String DELETE_ALL_QUIZZES = "/delete_all";

    @PostMapping(CREATE_QUIZ)
    public ResponseEntity<QuizEditorResponseDto> createQuiz(
            @RequestBody QuizEditorRequestToCreateDto dto) {

        return ResponseEntity.ok(quizService.createQuiz(dto));
    }

    @PatchMapping(EDIT_QUIZ_BY_ID)
    public ResponseEntity<QuizEditorResponseDto> editQuizById(
            @PathVariable("quiz_id") Long quizId,
            @RequestBody QuizEditorRequestToEditDto dto) {

        return ResponseEntity.ok(quizService.editQuizById(quizId, dto));
    }

    @GetMapping(GET_QUIZ_BY_ID)
    public ResponseEntity<QuizEditorResponseDto> getQuizById(
            @PathVariable("quiz_id") Long quizId) {

        return ResponseEntity.ok(quizService.getQuizById(quizId));
    }

    @GetMapping(GET_ALL_QUIZZES)
    public ResponseEntity<List<QuizEditorResponseDto>> getAllQuizzes() {

        return ResponseEntity.ok(quizService.getAllQuizzes());
    }

    @DeleteMapping(DELETE_QUIZ_BY_ID)
    public ResponseEntity<String> deleteQuizById(
            @PathVariable("quiz_id") Long quizId) {

        return ResponseEntity.ok(quizService.deleteQuizById(quizId));
    }

    @DeleteMapping(DELETE_ALL_QUIZZES)
    public ResponseEntity<String> deleteAllQuizzes() {

        return ResponseEntity.ok(quizService.deleteAllQuizzes());
    }

}
