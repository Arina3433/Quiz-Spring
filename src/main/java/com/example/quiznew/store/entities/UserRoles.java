package com.example.quiznew.store.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum UserRoles {

    @JsonProperty("student")
    STUDENT("student"),

    @JsonProperty("teacher")
    TEACHER("teacher");

    private String value;

    private UserRoles(String value) {
        this.value = value;
    }

    public static UserRoles toUserRoleFromString(String value) {
        if (value != null) {
            for (UserRoles roles : UserRoles.values()) {
                if (value.equalsIgnoreCase(roles.value)) {
                    return roles;
                }
            }
        }
        throw new IllegalArgumentException(String.format(
                "User role %s wasn't found.", value)
        );
    }

}

//    PriorityType low = PriorityType.fromString("lowpriority");
