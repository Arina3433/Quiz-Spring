package com.example.quiznew.api.converters;

import com.example.quiznew.api.dtos.QuestionDto;
import com.example.quiznew.store.entities.Question;
import com.example.quiznew.store.entities.Quiz;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Conditions;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class QuestionConverter {

    private final ModelMapper modelMapper;

    public Question convertToQuestion(QuestionDto dto) {
        return modelMapper.map(dto, Question.class);
    }

    public QuestionDto convertToQuestionDto(Question entity) {
        return modelMapper.map(entity, QuestionDto.class);
    }

    public List<Question> convertToQuestionList(List<QuestionDto> dtoList) {

        return dtoList.stream()
                .map(dto -> modelMapper.map(dto, Question.class))
                .collect(Collectors.toList());
    }

    public List<QuestionDto> convertToQuestionDtoList(List<Question> entityList) {

        return entityList.stream()
                .map(entity -> modelMapper.map(entity, QuestionDto.class))
                .collect(Collectors.toList());
    }

    @PostConstruct
    public void setupMapper() {

        modelMapper
                .getConfiguration()
                .setPropertyCondition(Conditions.isNotNull());

        modelMapper
                .createTypeMap(Question.class, QuestionDto.class)
                .addMappings(map -> map
                        .using(quizListToQuizIdListConverter)
                        .map(
                                Question::getQuizzesList,
                                QuestionDto::setQuizzesIdList
                        )
                );
    }

    Converter<List<Quiz>, List<Long>> quizListToQuizIdListConverter =
            context -> context.getSource()
                    .stream()
                    .mapToLong(Quiz::getId)
                    .boxed()
                    .collect(Collectors.toList());

}
