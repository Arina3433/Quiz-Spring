package com.example.quiznew.api.controllers.teacher;

import com.example.quiznew.api.dtos.teacher.AnswerEditorDto;
import com.example.quiznew.api.services.teacher.implementation.AnswerEditorServiceImpl;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/quiz/app/teacher/questions")
public class AnswerEditorController {

    AnswerEditorServiceImpl answerService;

    static final String CREATE_ANSWER = "/{question_id}/answers/create";
    static final String EDIT_ANSWER_BY_ID = "/answers/edit/{answer_id}";
    static final String DELETE_ANSWER_BY_ID = "/answers/delete/{answer_id}";
    static final String DELETE_ALL_ANSWERS_BY_QUESTION_ID = "/answers/delete_all/{question_id}";

    @Transactional
    @PostMapping(CREATE_ANSWER)
    public ResponseEntity<AnswerEditorDto> createAnswer(
            @PathVariable("question_id") Long questionId,
            @RequestParam("answer_text") String answerText,
            @RequestParam(value = "is_correct", required = false) Optional<Boolean> optionalIsCorrect) {

        return ResponseEntity.ok(answerService.createAnswer(questionId, answerText, optionalIsCorrect));
    }

    @Transactional
    @PatchMapping(EDIT_ANSWER_BY_ID)
    public ResponseEntity<AnswerEditorDto> editAnswerById(
            @PathVariable("answer_id") Long answerId,
            @RequestParam(value = "answer_text", required = false) Optional<String> optionalAnswerText,
            @RequestParam(value = "is_correct", required = false) Optional<Boolean> optionalIsCorrect) {

        return ResponseEntity.ok(answerService.editAnswerById(answerId, optionalAnswerText, optionalIsCorrect));
    }

    @Transactional
    @DeleteMapping(DELETE_ANSWER_BY_ID)
    public ResponseEntity<String> deleteAnswerById(
            @PathVariable("answer_id") Long answerId) {

        return ResponseEntity.ok(answerService.deleteAnswerById(answerId));
    }

    @Transactional
    @DeleteMapping(DELETE_ALL_ANSWERS_BY_QUESTION_ID)
    public ResponseEntity<String> deleteAllAnswersByQuestionId(
            @PathVariable("question_id") Long questionId) {

        return ResponseEntity.ok(answerService.deleteAllAnswersByQuestionId(questionId));
    }

}
