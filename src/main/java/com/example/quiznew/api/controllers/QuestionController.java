package com.example.quiznew.api.controllers;

import com.example.quiznew.api.dtos.QuestionDto;
import com.example.quiznew.api.services.QuestionServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class QuestionController {

    QuestionServiceImpl questionService;

    static final String CREATE_QUESTION = "/questions/create";
    static final String EDIT_QUESTION_BY_ID = "/questions/edit/{question_id}";
    static final String GET_QUESTION_BY_ID = "/questions/get/{question_id}";
    static final String GET_ALL_QUESTIONS = "/questions/get_all";
    static final String DELETE_QUESTION_BY_ID = "/questions/delete/{question_id}";
    static final String DELETE_ALL_QUESTIONS = "/questions/delete_all";

    @PostMapping(CREATE_QUESTION)
    public ResponseEntity<QuestionDto> createQuestion(
            @RequestParam("question_text") String questionText) {

        return ResponseEntity.ok(questionService.createQuestion(questionText));
    }

    @PatchMapping(EDIT_QUESTION_BY_ID)
    public ResponseEntity<QuestionDto> editQuestionById(
            @PathVariable("question_id") Long questionId,
            @RequestParam("question_text") String questionText) {

        return ResponseEntity.ok(questionService.editQuestionById(questionId, questionText));
    }

    @GetMapping(GET_QUESTION_BY_ID)
    public ResponseEntity<QuestionDto> getQuestionById(
            @PathVariable("question_id") Long questionId) {

        return ResponseEntity.ok(questionService.getQuestionById(questionId));
    }

    @GetMapping(GET_ALL_QUESTIONS)
    public ResponseEntity<List<QuestionDto>> getAllQuestions() {

        return ResponseEntity.ok(questionService.getAllQuestions());
    }

    @DeleteMapping(DELETE_QUESTION_BY_ID)
    public ResponseEntity<String> deleteQuestionById(
            @PathVariable("question_id") Long questionId) {

        return ResponseEntity.ok(questionService.deleteQuestionById(questionId));
    }

    @DeleteMapping(DELETE_ALL_QUESTIONS)
    public ResponseEntity<String> deleteAllQuestions() {

        return ResponseEntity.ok(questionService.deleteAllQuestions());
    }

}
