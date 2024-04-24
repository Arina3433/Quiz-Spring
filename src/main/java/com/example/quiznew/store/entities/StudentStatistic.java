package com.example.quiznew.store.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "student_statistic")
public class StudentStatistic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String studentName;

    Long quizId;

    String studentResult;

    Date dateOfCompletionQuiz;

}
