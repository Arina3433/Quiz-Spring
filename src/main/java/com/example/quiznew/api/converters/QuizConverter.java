package com.example.quiznew.api.converters;

import com.example.quiznew.api.dtos.QuizDtoRequestToCreate;
import com.example.quiznew.api.dtos.QuizDtoResponse;
import com.example.quiznew.store.entities.Quiz;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class QuizConverter {

    private final ModelMapper modelMapper;

    public Quiz convertToQuiz(QuizDtoRequestToCreate dto) {
        return modelMapper.map(dto, Quiz.class);
    }

    public QuizDtoResponse convertToQuizDto(Quiz entity) {
        return modelMapper.map(entity, QuizDtoResponse.class);
    }

    public List<Quiz> convertToQuizList(List<QuizDtoRequestToCreate> dtoList) {

        return dtoList.stream()
                .map(dto -> modelMapper.map(dto, Quiz.class))
                .collect(Collectors.toList());
    }

    public List<QuizDtoResponse> convertToQuizDtoList(List<Quiz> entityList) {

        return entityList.stream()
                .map(entity -> modelMapper.map(entity, QuizDtoResponse.class))
                .collect(Collectors.toList());
    }

}
