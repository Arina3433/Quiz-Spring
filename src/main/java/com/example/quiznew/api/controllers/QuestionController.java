package com.example.quiznew.api.controllers;

import com.example.quiznew.api.dtos.QuestionDto;
import com.example.quiznew.api.services.implementation.QuestionServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/quiz/app/teacher/questions")
public class QuestionController {

    QuestionServiceImpl questionService;

    static final String CREATE_QUESTION = "/create";
    static final String EDIT_QUESTION_BY_ID = "/edit/{question_id}";
    static final String GET_QUESTION_BY_ID = "get/{question_id}";
    static final String GET_ALL_QUESTIONS_WITH_FILTER = "/get_all";
    static final String DELETE_QUESTION_BY_ID = "/delete/{question_id}";
    static final String DELETE_ALL_QUESTIONS = "/delete_all";

    @PostMapping(CREATE_QUESTION)
    public ResponseEntity<QuestionDto> createQuestion(
            @RequestParam("question_text") String questionText,
            @RequestParam(value = "question_category", required = false) Optional<String> optionalQuestionCategory) {

        return ResponseEntity.ok(questionService.createQuestion(questionText, optionalQuestionCategory));
    }

    @PatchMapping(EDIT_QUESTION_BY_ID)
    public ResponseEntity<QuestionDto> editQuestionById(
            @PathVariable("question_id") Long questionId,
            @RequestParam(value = "question_text", required = false) Optional<String> optionalQuestionText,
            @RequestParam(value = "question_category", required = false) Optional<String> optionalQuestionCategory) {

        return ResponseEntity.ok(questionService.editQuestionById(questionId, optionalQuestionText, optionalQuestionCategory));
    }

    @GetMapping(GET_QUESTION_BY_ID)
    public ResponseEntity<QuestionDto> getQuestionById(
            @PathVariable("question_id") Long questionId) {

        return ResponseEntity.ok(questionService.getQuestionById(questionId));
    }

    @GetMapping(GET_ALL_QUESTIONS_WITH_FILTER)
    public ResponseEntity<List<QuestionDto>> getAllQuestions(
            @RequestParam(value = "question_category", required = false) Optional<String> optionalQuestionCategory) {

        return ResponseEntity.ok(questionService.getAllQuestions(optionalQuestionCategory));
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
