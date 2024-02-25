package com.example.quiznew.store.repositories;

import com.example.quiznew.store.entities.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

}
