package com.example.quiznew.store.entities;

import com.example.quiznew.api.exceptions.BadRequestException;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.StringUtils;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum UserRoles {

    @JsonProperty("student")
    STUDENT("student"),

    @JsonProperty("teacher")
    TEACHER("teacher");

    String value;

    public static UserRoles toUserRoleFromString(String value) {

        if (!StringUtils.isBlank(value)) {

            for (UserRoles roles : UserRoles.values()) {
                if (value.equalsIgnoreCase(roles.value)) {
                    return roles;
                }
            }

            throw new BadRequestException(String.format(
                    "User role %s wasn't found.", value)
            );

        } else {
            throw new BadRequestException("User role can't be empty");
        }
    }

}
