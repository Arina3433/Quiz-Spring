package com.example.quiznew.api.converters.teacher;

import com.example.quiznew.api.dtos.teacher.AnswerEditorDto;
import com.example.quiznew.store.entities.Answer;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AnswerEditorConverter {

    ModelMapper modelMapper;

    public Answer convertToAnswer(AnswerEditorDto dto) {
        return modelMapper.map(dto, Answer.class);
    }

    public AnswerEditorDto convertToAnswerDto(Answer entity) {
        return modelMapper.map(entity, AnswerEditorDto.class);
    }

}
