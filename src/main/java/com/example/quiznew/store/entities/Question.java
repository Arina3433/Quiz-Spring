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
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "questions_generator")
    @SequenceGenerator(name = "questions_generator", sequenceName = "questions_seq", allocationSize = 1)
    Long id;

    @Column(unique = true)
    String questionText;

    @Enumerated(EnumType.STRING)
    QuestionCategories questionCategories;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "question_id")
    List<Answer> answersList = new ArrayList<>();
    // Владелец связи

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "questionsInQuizList")
    List<Quiz> quizzesList = new ArrayList<>();

}
