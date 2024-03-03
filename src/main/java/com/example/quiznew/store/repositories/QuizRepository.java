package com.example.quiznew.store.repositories;

import com.example.quiznew.store.entities.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    Optional<Quiz> findQuizByQuizName(String quizName);
}
