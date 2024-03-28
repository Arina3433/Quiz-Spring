package com.example.quiznew.api.converters;

import com.example.quiznew.api.dtos.UserDto;
import com.example.quiznew.store.entities.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserConverter {

    private final ModelMapper modelMapper;

    public User convertToUser(UserDto dto) {
        return modelMapper.map(dto, User.class);
    }

    public UserDto convertToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

}
