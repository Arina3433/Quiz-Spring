package com.example.quiznew.store.repositories;

import com.example.quiznew.store.entities.StudentStatistic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentStatisticRepository extends JpaRepository<StudentStatistic, Long> {

    Optional<List<StudentStatistic>> findStudentStatisticByStudentName(String studentName);

    Optional<List<StudentStatistic>> findStudentStatisticByQuizId(Long quizId);

    Optional<List<StudentStatistic>> findStudentStatisticByStudentNameAndQuizId(String studentName, Long quizId);

}
