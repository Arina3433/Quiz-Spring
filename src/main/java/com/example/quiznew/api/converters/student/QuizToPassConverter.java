package com.example.quiznew.api.converters.student;

import com.example.quiznew.api.dtos.student.QuizToPassDto;
import com.example.quiznew.store.entities.Quiz;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class QuizToPassConverter {

    ModelMapper modelMapper;

    public Quiz convertToQuiz(QuizToPassDto dto) {
        return modelMapper.map(dto, Quiz.class);
    }

    public QuizToPassDto convertToQuizToPassDto(Quiz entity) {
        return modelMapper.map(entity, QuizToPassDto.class);
    }

}
