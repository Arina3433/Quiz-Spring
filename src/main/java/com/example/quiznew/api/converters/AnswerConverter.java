package com.example.quiznew.api.converters;

import com.example.quiznew.api.dtos.AnswerDto;
import com.example.quiznew.store.entities.Answer;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AnswerConverter {

    private final ModelMapper modelMapper;

    public Answer convertToAnswer(AnswerDto dto) {
        return modelMapper.map(dto, Answer.class);
    }

    public AnswerDto convertToAnswerDto(Answer entity) {
        return modelMapper.map(entity, AnswerDto.class);
    }

}
