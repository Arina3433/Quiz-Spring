package com.example.quiznew.store.repositories;

import com.example.quiznew.store.entities.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Optional<Answer> findByAnswerText(String answerText);

    void deleteAllByQuestionId(Long questionId);

}
