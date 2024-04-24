package com.example.quiznew.api.converters.student;

import com.example.quiznew.api.dtos.student.StudentStatisticDto;
import com.example.quiznew.store.entities.StudentStatistic;
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
public class StudentStatisticConverter {

    ModelMapper modelMapper;

    public StudentStatistic convertToStudentStatistic(StudentStatisticDto dto) {
        return modelMapper.map(dto, StudentStatistic.class);
    }

    public StudentStatisticDto convertToStudentStatisticDto(StudentStatistic studentStatistic) {
        return modelMapper.map(studentStatistic, StudentStatisticDto.class);
    }

    public List<StudentStatistic> convertToStudentStatisticList(List<StudentStatisticDto> dtoList) {

        return dtoList
                .stream()
                .map(dto -> modelMapper.map(dto, StudentStatistic.class))
                .collect(Collectors.toList());
    }

    public List<StudentStatisticDto> convertToStudentStatisticDtoList(List<StudentStatistic> studentStatisticList) {

        return studentStatisticList
                .stream()
                .map(studentStatistic -> modelMapper.map(studentStatistic, StudentStatisticDto.class))
                .collect(Collectors.toList());
    }

}
