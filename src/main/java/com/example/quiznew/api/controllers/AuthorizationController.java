package com.example.quiznew.api.controllers;

import com.example.quiznew.api.dtos.JwtAuthenticationResponseDto;
import com.example.quiznew.api.dtos.RefreshTokenRequestDto;
import com.example.quiznew.api.dtos.SignInRequestDto;
import com.example.quiznew.api.dtos.SignUpRequestDto;
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
@RequestMapping("/quiz/auth")
public class AuthorizationController {

    AuthorizationServiceImpl authorizationService;

    @PostMapping("/sign-up")
    public ResponseEntity<String> signUp(
            @RequestBody SignUpRequestDto signUpRequest) {
        return ResponseEntity.ok(authorizationService.signUp(signUpRequest));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<JwtAuthenticationResponseDto> signIn(
            @RequestBody SignInRequestDto signingRequest) {
        return ResponseEntity.ok(authorizationService.signIn(signingRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthenticationResponseDto> refresh(
            @RequestBody RefreshTokenRequestDto refreshTokenRequest) {
        return ResponseEntity.ok(authorizationService.refreshToken(refreshTokenRequest));
    }

}
