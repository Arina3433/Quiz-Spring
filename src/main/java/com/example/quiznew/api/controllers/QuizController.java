package com.example.quiznew.api.controllers;

import com.example.quiznew.api.dtos.QuizDtoRequestToCreate;
import com.example.quiznew.api.dtos.QuizDtoRequestToEdit;
import com.example.quiznew.api.dtos.QuizDtoResponse;
import com.example.quiznew.api.services.implementation.QuizServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@PreAuthorize("hasAuthority('TEACHER')")
@RequestMapping("/quiz/app")
public class QuizController {

    QuizServiceImpl quizService;

    static final String CREATE_QUIZ = "/quizzes/create";
    static final String EDIT_QUIZ_BY_ID = "/quizzes/edit/{quiz_id}";
    static final String GET_QUIZ_BY_ID = "/quizzes/get/{quiz_id}";
    static final String GET_ALL_QUIZZES = "/quizzes/get_all";
    static final String DELETE_QUIZ_BY_ID = "/quizzes/delete/{quiz_id}";
    static final String DELETE_ALL_QUIZZES = "/quizzes/delete_all";

    @PostMapping(CREATE_QUIZ)
    public ResponseEntity<QuizDtoResponse> createQuiz(
            @RequestBody QuizDtoRequestToCreate dto) {

        return ResponseEntity.ok(quizService.createQuiz(dto));
    }

    @PatchMapping(EDIT_QUIZ_BY_ID)
    public ResponseEntity<QuizDtoResponse> editQuizById(
            @PathVariable("quiz_id") Long quizId,
            @RequestBody QuizDtoRequestToEdit dto) {

        return ResponseEntity.ok(quizService.editQuizById(quizId, dto));
    }

    @GetMapping(GET_QUIZ_BY_ID)
    public ResponseEntity<QuizDtoResponse> getQuizById(
            @PathVariable("quiz_id") Long quizId) {

        return ResponseEntity.ok(quizService.getQuizById(quizId));
    }

    @GetMapping(GET_ALL_QUIZZES)
    public ResponseEntity<List<QuizDtoResponse>> getAllQuizzes() {

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
