package com.example.quiznew.api.utils.rabbit.dto;

import com.example.quiznew.store.entities.user.UserRole;

public record UserRegisteredEventDto(
        Long userId,
        String username,
        UserRole userRole
) {
}