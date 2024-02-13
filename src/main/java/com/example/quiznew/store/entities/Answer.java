package com.example.quiznew.store.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
// TODO может убрать билдер?
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "answers")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "answers_generator")
    @SequenceGenerator(name = "answers_generator", sequenceName = "answers_seq", allocationSize = 1)
    Long id;

    String answerText;

    Boolean isCorrect;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    Question question;

}
