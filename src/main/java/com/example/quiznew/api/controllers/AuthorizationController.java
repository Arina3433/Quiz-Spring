package com.example.quiznew.api.controllers;

import com.example.quiznew.api.dtos.UserDto;
import com.example.quiznew.api.services.implementation.AuthorizationServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/quiz/app")
public class AuthorizationController {

    AuthorizationServiceImpl authorizationService;

    @PostMapping("/new-user")
    public ResponseEntity<String> addNewUser(@RequestBody UserDto dto) {
        return ResponseEntity.ok(authorizationService.addUser(dto));
    }

}
