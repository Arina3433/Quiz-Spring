package com.example.quiznew.api.services.student;

import com.example.quiznew.api.dtos.student.StudentStatisticDto;

import java.util.List;
import java.util.Optional;

public interface StudentStatisticForStudentService {

    List<StudentStatisticDto> getStudentStatistic(Optional<String> optionalQuizId);

}
