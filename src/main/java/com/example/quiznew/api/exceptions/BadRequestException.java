package com.example.quiznew.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }

}

