package com.example.quiznew.store.repositories;

import com.example.quiznew.store.entities.Answer;
import com.example.quiznew.store.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Optional<Answer> findByAnswerText(String answerText);

    void deleteAllByQuestionId(Long questionId);

    List<Answer> findAllById(Iterable answersId);

}
