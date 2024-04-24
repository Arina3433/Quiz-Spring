package com.example.quiznew.api.services.teacher;

import com.example.quiznew.api.dtos.student.StudentStatisticDto;

import java.util.List;
import java.util.Optional;

public interface StudentStatisticForTeacherService {

    List<StudentStatisticDto> getStudentStatistic(Optional<String> optionalStudentName, Optional<String> optionalQuizId);

}
