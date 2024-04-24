package com.example.quiznew.api.services.student.implementation;

import com.example.quiznew.api.converters.student.QuizToPassConverter;
import com.example.quiznew.api.dtos.student.QuizToPassDto;
import com.example.quiznew.api.dtos.student.StudentResultDto;
import com.example.quiznew.api.exceptions.BadRequestException;
import com.example.quiznew.api.services.helper.ServiceHelper;
import com.example.quiznew.api.services.student.QuizPassService;
import com.example.quiznew.store.entities.Answer;
import com.example.quiznew.store.entities.Quiz;
import com.example.quiznew.store.entities.StudentStatistic;
import com.example.quiznew.store.repositories.StudentStatisticRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class QuizPassServiceImpl implements QuizPassService {

    StudentStatisticRepository studentStatisticRepository;

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

        serviceHelper.findDuplicatesInRequestAndThrow(answersId);

        List<Answer> answerList = serviceHelper.getNeededAnswersByIdOrElseThrow(answersId);

        serviceHelper.checkIfThereAreAnswersInQuizOrElseThrow(answersId, quiz);

        float studentsResult = 0;

        for (Answer el : answerList) {

            if (el.getIsCorrect()) {
                studentsResult++;
            }
        }

        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String resultInPercentage =
                decimalFormat.format(studentsResult * 100 / quiz.getQuestionsInQuizList().size()) + "%";

        String studentName = SecurityContextHolder.getContext().getAuthentication().getName();

        studentStatisticRepository.saveAndFlush(
                StudentStatistic.builder()
                        .studentName(studentName)
                        .quizId(quizId)
                        .studentResult(resultInPercentage)
                        .dateOfCompletionQuiz(new Date())
                        .build()
        );

        return StudentResultDto
                .builder()
                .studentName(studentName)
                .quizName(quiz.getQuizName())
                .studentResult(resultInPercentage)
                .build();
    }

}
