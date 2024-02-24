package com.example.quiznew.store.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Categories {

    @JsonProperty("math")
    MATH,

    @JsonProperty("literature")
    LITERATURE,

    @JsonProperty("physics")
    PHYSICS,

    @JsonProperty("chemistry")
    CHEMISTRY,

    @JsonProperty("biology")
    BIOLOGY,

    @JsonProperty("common")
    COMMON

}
