package com.example.quiznew.store.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "quizzes")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quizzes_generator")
    @SequenceGenerator(name = "quizzes_generator", sequenceName = "quizzes_seq", allocationSize = 1)
    Long id;

    @Column(unique = true)
    String quizName;

    @ManyToMany
    @JoinTable(name = "questions_in_quiz",
            joinColumns = @JoinColumn(name = "quiz_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id"))
    List<Question> questionsInQuizList = new ArrayList<>();
    // Владелец связи

}
