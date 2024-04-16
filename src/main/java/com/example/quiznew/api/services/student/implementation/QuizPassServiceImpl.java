package com.example.quiznew.api.services.student.implementation;

import com.example.quiznew.api.converters.student.QuizToPassConverter;
import com.example.quiznew.api.dtos.student.QuizToPassDto;
import com.example.quiznew.api.dtos.student.StudentResultDto;
import com.example.quiznew.api.exceptions.BadRequestException;
import com.example.quiznew.api.services.helper.ServiceHelper;
import com.example.quiznew.api.services.student.QuizPassService;
import com.example.quiznew.store.entities.Answer;
import com.example.quiznew.store.entities.Quiz;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class QuizPassServiceImpl implements QuizPassService {

    QuizToPassConverter quizConverter;

    ServiceHelper serviceHelper;

    @Override
    @Transactional
    public QuizToPassDto getQuizToPass(Long quizId) {

        Quiz quiz = serviceHelper.getQuizByIdOrElseThrow(quizId);

        return quizConverter.convertToQuizToPassDto(quiz);
    }

    @Override
    @Transactional
    public StudentResultDto passQuizAndGetResults(Long quizId, List<Long> answersId) {

        Quiz quiz = serviceHelper.getQuizByIdOrElseThrow(quizId);

        if (quiz.getQuestionsInQuizList().size() < answersId.size()) {
            throw new BadRequestException("There is one correct answer for every question. " +
                    "The number of answers cannot exceed the number of questions.");
        }

        List<Answer> answerList = serviceHelper.getNeededAnswersByIdOrElseThrow(answersId);

        serviceHelper.findDuplicatesInRequestAndThrow(answersId);

        serviceHelper.checkIfThereAreAnswersInQuizOrElseThrow(answersId, quiz);

        int studentsResult = 0;

        for (Answer el : answerList) {

            if (el.getIsCorrect()) {
                studentsResult++;
            }
        }

        return StudentResultDto
                .builder()
                .studentName(SecurityContextHolder.getContext().getAuthentication().getName())
                .quizName(quiz.getQuizName())
                .studentResult(studentsResult + "/" + quiz.getQuestionsInQuizList().size())
                .build();
    }

}
