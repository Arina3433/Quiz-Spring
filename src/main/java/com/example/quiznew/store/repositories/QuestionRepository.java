package com.example.quiznew.store.repositories;

import com.example.quiznew.store.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    Optional<Question> findByQuestionText(String questionText);

    List<Question> findAllBy();

}
