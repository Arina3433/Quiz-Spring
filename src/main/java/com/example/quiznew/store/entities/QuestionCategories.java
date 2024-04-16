package com.example.quiznew.store.entities;

import com.example.quiznew.api.exceptions.BadRequestException;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.StringUtils;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum QuestionCategories {

    @JsonProperty("math")
    MATH("math"),

    @JsonProperty("literature")
    LITERATURE("literature"),

    @JsonProperty("physics")
    PHYSICS("physics"),

    @JsonProperty("chemistry")
    CHEMISTRY("chemistry"),

    @JsonProperty("biology")
    BIOLOGY("biology"),

    @JsonProperty("common")
    COMMON("common");

    String value;

    public static QuestionCategories toQuestionCategoryFromString(String value) {

        if (!StringUtils.isBlank(value)) {

            for (QuestionCategories categories : QuestionCategories.values()) {
                if (value.equalsIgnoreCase(categories.value)) {
                    return categories;
                }
            }

            throw new BadRequestException(String.format(
                    "Question category %s wasn't found.", value)
            );

        } else {
            return COMMON;
        }
    }

}
