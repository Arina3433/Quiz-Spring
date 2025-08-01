package com.example.quiznew.store.entities.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum UserRole {

    STUDENT("Учащийся"),
    TEACHER("Преподаватель");

    private final String value;
}
