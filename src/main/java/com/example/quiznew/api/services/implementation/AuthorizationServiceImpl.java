package com.example.quiznew.api.services.implementation;

import com.example.quiznew.api.converters.UserConverter;
import com.example.quiznew.api.dtos.UserDto;
import com.example.quiznew.api.exceptions.BadRequestException;
import com.example.quiznew.api.services.AuthorizationService;
import com.example.quiznew.store.entities.User;
import com.example.quiznew.store.entities.UserRoles;
import com.example.quiznew.store.repositories.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/quiz/app")
public class AuthorizationServiceImpl implements AuthorizationService {

    UserRepository userRepository;
    UserConverter userConverter;
    PasswordEncoder encoder;

    @Override
    public String addUser(UserDto dto) {

        if (dto.getUsername().isBlank()) {
            throw new BadRequestException("Username can't be empty");
        }

        if (dto.getPassword().isBlank()) {
            throw new BadRequestException("Password can't be empty");
        }

        if (dto.getUserRoles().isBlank()) {
            throw new BadRequestException("User role can't be empty");
        }

        userRepository
                .findByUsername(dto.getUsername())
                .ifPresent(anotherUser -> {
                    throw new BadRequestException(
                            String.format(
                                    "User with the same name already exists with id %s.",
                                    anotherUser.getId()
                            )
                    );
                });

        User user = userRepository.saveAndFlush(
                User.builder()
                        .username(dto.getUsername())
                        .password(encoder.encode(dto.getPassword()))
                        .userRoles(UserRoles.toUserRoleFromString(dto.getUserRoles()))
                        .build()
        );

        return String.format("User %s is saved.", user.getUsername());
    }
}
