package com.example.quiznew.api.converters.teacher;

import com.example.quiznew.api.dtos.teacher.QuestionEditorDto;
import com.example.quiznew.store.entities.Question;
import com.example.quiznew.store.entities.Quiz;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.Conditions;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class QuestionEditorConverter {

    ModelMapper modelMapper;

    public Question convertToQuestion(QuestionEditorDto dto) {
        return modelMapper.map(dto, Question.class);
    }

    public QuestionEditorDto convertToQuestionDto(Question entity) {
        return modelMapper.map(entity, QuestionEditorDto.class);
    }

    public List<Question> convertToQuestionList(List<QuestionEditorDto> dtoList) {

        return dtoList.stream()
                .map(dto -> modelMapper.map(dto, Question.class))
                .collect(Collectors.toList());
    }

    public List<QuestionEditorDto> convertToQuestionDtoList(List<Question> entityList) {

        return entityList.stream()
                .map(entity -> modelMapper.map(entity, QuestionEditorDto.class))
                .collect(Collectors.toList());
    }

    @PostConstruct
    public void setupMapper() {

        modelMapper
                .getConfiguration()
                .setPropertyCondition(Conditions.isNotNull());

        modelMapper
                .createTypeMap(Question.class, QuestionEditorDto.class)
                .addMappings(map -> map
                        .using(quizListToQuizIdListConverter)
                        .map(
                                Question::getQuizzesList,
                                QuestionEditorDto::setQuizzesIdList
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
