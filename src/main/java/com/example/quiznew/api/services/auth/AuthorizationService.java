package com.example.quiznew.api.services.auth;

import com.example.quiznew.api.dtos.auth.JwtAuthenticationResponseDto;
import com.example.quiznew.api.dtos.auth.RefreshTokenRequestDto;
import com.example.quiznew.api.dtos.auth.SignInRequestDto;
import com.example.quiznew.api.dtos.auth.SignUpRequestDto;

public interface AuthorizationService {

    String signUp(SignUpRequestDto signUpRequest);

    JwtAuthenticationResponseDto signIn(SignInRequestDto signInRequest);

    JwtAuthenticationResponseDto refreshToken(RefreshTokenRequestDto refreshTokenRequest);

}
