package com.example.quiznew.api.services;

import com.example.quiznew.api.dtos.UserDto;
import com.example.quiznew.store.entities.User;

public interface AuthorizationService {

    String addUser(UserDto dto);

}
