package com.example.quiznew.api.services;

import com.example.quiznew.api.dtos.JwtAuthenticationResponseDto;
import com.example.quiznew.api.dtos.RefreshTokenRequestDto;
import com.example.quiznew.api.dtos.SignInRequestDto;
import com.example.quiznew.api.dtos.SignUpRequestDto;

public interface AuthorizationService {

    String signUp(SignUpRequestDto signUpRequest);

    JwtAuthenticationResponseDto signIn(SignInRequestDto signInRequest);

    JwtAuthenticationResponseDto refreshToken(RefreshTokenRequestDto refreshTokenRequest);

}
