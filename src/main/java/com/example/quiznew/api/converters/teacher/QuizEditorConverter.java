package com.example.quiznew.api.converters.teacher;

import com.example.quiznew.api.dtos.teacher.QuizEditorRequestToCreateDto;
import com.example.quiznew.api.dtos.teacher.QuizEditorResponseDto;
import com.example.quiznew.store.entities.Quiz;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class QuizEditorConverter {

    ModelMapper modelMapper;

    public Quiz convertToQuiz(QuizEditorRequestToCreateDto dto) {
        return modelMapper.map(dto, Quiz.class);
    }

    public QuizEditorResponseDto convertToQuizDto(Quiz entity) {
        return modelMapper.map(entity, QuizEditorResponseDto.class);
    }

    public List<Quiz> convertToQuizList(List<QuizEditorRequestToCreateDto> dtoList) {

        return dtoList.stream()
                .map(dto -> modelMapper.map(dto, Quiz.class))
                .collect(Collectors.toList());
    }

    public List<QuizEditorResponseDto> convertToQuizDtoList(List<Quiz> entityList) {

        return entityList.stream()
                .map(entity -> modelMapper.map(entity, QuizEditorResponseDto.class))
                .collect(Collectors.toList());
    }

}
