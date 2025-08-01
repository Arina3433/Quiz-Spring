package com.example.quiznew.api.utils.rabbit.dto;

import com.example.quiznew.store.entities.user.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisteredEventDto {
    private Long userId;
    private String username;
    private UserRole userRole;
}
