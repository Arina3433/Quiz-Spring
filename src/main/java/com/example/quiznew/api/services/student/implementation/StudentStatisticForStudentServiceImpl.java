package com.example.quiznew.api.services.student.implementation;

import com.example.quiznew.api.converters.student.StudentStatisticConverter;
import com.example.quiznew.api.dtos.student.StudentStatisticDto;
import com.example.quiznew.api.exceptions.BadRequestException;
import com.example.quiznew.api.services.helper.ServiceHelper;
import com.example.quiznew.api.services.student.StudentStatisticForStudentService;
import com.example.quiznew.store.entities.StudentStatistic;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StudentStatisticForStudentServiceImpl implements StudentStatisticForStudentService {

    StudentStatisticConverter studentStatisticConverter;

    ServiceHelper serviceHelper;

    @Override
    public List<StudentStatisticDto> getStudentStatistic(Optional<String> optionalQuizId) {

        String studentName = SecurityContextHolder.getContext().getAuthentication().getName();

        Long quizId = serviceHelper.getaLong(optionalQuizId);

        List<StudentStatistic> studentStatisticList =
                quizId != null ?
                serviceHelper.getStudentStatisticsByStudentNameAndQuizIdOrElseThrow(studentName, quizId) :
                serviceHelper.getStudentStatisticsByStudentNameOrElseThrow(studentName);

        return studentStatisticConverter.convertToStudentStatisticDtoList(studentStatisticList);
    }

}
