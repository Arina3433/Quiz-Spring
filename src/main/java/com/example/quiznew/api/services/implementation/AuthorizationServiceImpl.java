package com.example.quiznew.api.services.implementation;

import com.example.quiznew.api.dtos.JwtAuthenticationResponseDto;
import com.example.quiznew.api.dtos.RefreshTokenRequestDto;
import com.example.quiznew.api.dtos.SignInRequestDto;
import com.example.quiznew.api.dtos.SignUpRequestDto;
import com.example.quiznew.api.exceptions.BadRequestException;
import com.example.quiznew.api.services.AuthorizationService;
import com.example.quiznew.api.services.JWTService;
import com.example.quiznew.api.services.helpers.ServiceHelper;
import com.example.quiznew.store.entities.User;
import com.example.quiznew.store.entities.UserRoles;
import com.example.quiznew.store.repositories.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthorizationServiceImpl implements AuthorizationService {

    UserRepository userRepository;
    PasswordEncoder encoder;

    AuthenticationManager authenticationManager;
    JWTService jwtService;

    ServiceHelper serviceHelper;

    @Override
    public String signUp(SignUpRequestDto signUpRequest) {

        if (signUpRequest.getUsername().isBlank()) {
            throw new BadRequestException("Username can't be empty");
        }

        if (signUpRequest.getPassword().isBlank()) {
            throw new BadRequestException("Password can't be empty");
        }

        if (signUpRequest.getUserRoles().isBlank()) {
            throw new BadRequestException("User role can't be empty");
        }

        userRepository
                .findByUsername(signUpRequest.getUsername())
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
                        .username(signUpRequest.getUsername())
                        .password(encoder.encode(signUpRequest.getPassword()))
                        .userRoles(UserRoles.toUserRoleFromString(signUpRequest.getUserRoles()))
                        .build()
        );

        return String.format("User %s is saved.", user.getUsername());
    }

    @Override
    public JwtAuthenticationResponseDto signIn(SignInRequestDto signInRequest) {

        User user = serviceHelper.getUserByUsernameOrElseThrow(signInRequest.getUsername());

        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        signInRequest.getUsername(), signInRequest.getPassword())
                );

        String token = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return JwtAuthenticationResponseDto.builder()
                .token(token)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public JwtAuthenticationResponseDto refreshToken(RefreshTokenRequestDto refreshTokenRequest) {

        String username = jwtService.extractUserName(refreshTokenRequest.getRefreshToken());
        User user = serviceHelper.getUserByUsernameOrElseThrow(username);

        if (jwtService.isTokenValid(refreshTokenRequest.getRefreshToken(), user)) {

            String token = jwtService.generateToken(user);

            return JwtAuthenticationResponseDto.builder()
                    .token(token)
                    .refreshToken(refreshTokenRequest.getRefreshToken())
                    .build();
        } else
            throw new BadRequestException("Invalid refresh token");
    }

}
