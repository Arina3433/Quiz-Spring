package com.example.quiznew.api.converters.user;

import com.example.quiznew.api.utils.rabbit.dto.UserRegisteredEventDto;
import com.example.quiznew.store.entities.user.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserConverter {
    private final ModelMapper modelMapper;

    public User toUser(UserRegisteredEventDto dto) {
        return modelMapper.map(dto, User.class);
    }
}
