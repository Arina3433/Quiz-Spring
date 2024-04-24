package com.example.quiznew.api.services.teacher.implementation;

import com.example.quiznew.api.converters.student.StudentStatisticConverter;
import com.example.quiznew.api.dtos.student.StudentStatisticDto;
import com.example.quiznew.api.services.helper.ServiceHelper;
import com.example.quiznew.api.services.teacher.StudentStatisticForTeacherService;
import com.example.quiznew.store.entities.StudentStatistic;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StudentStatisticForTeacherServiceImpl implements StudentStatisticForTeacherService {

    StudentStatisticConverter studentStatisticConverter;

    ServiceHelper serviceHelper;

    @Transactional
    @Override
    public List<StudentStatisticDto> getStudentStatistic(
            Optional<String> optionalStudentName, Optional<String> optionalQuizId) {

        optionalStudentName = optionalStudentName.filter(studentName -> !studentName.isBlank());

        Long quizId = serviceHelper.getaLong(optionalQuizId);

        List<StudentStatistic> studentStatisticList = optionalStudentName
                .map(studentName -> {
                    if (quizId != null) {
                        return serviceHelper.getStudentStatisticsByStudentNameAndQuizIdOrElseThrow(studentName, quizId);
                    } else {
                        return serviceHelper.getStudentStatisticsByStudentNameOrElseThrow(studentName);
                    }
                })
                .orElseGet(() -> {
                    if (quizId != null) {
                        return serviceHelper.getStudentStatisticsByQuizIdOrElseThrow(quizId);
                    } else {
                        return serviceHelper.getStudentStatisticsOrElseThrow();
                    }
                });

        return studentStatisticConverter.convertToStudentStatisticDtoList(studentStatisticList);
    }

}
