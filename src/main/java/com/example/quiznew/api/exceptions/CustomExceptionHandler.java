package com.example.quiznew.api.exceptions;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Log4j2
// Автоматически внедряет Logger
// Логгер - это инструмент для записи информации о работе приложения
@ControllerAdvice
// Используется для создания глобального контроллера обработки исключений. Класс, помеченный этой аннотацией,
// становится централизованным местом для обработки исключений, которые могут возникнуть в различных контроллерах приложения.
// Методы в таком классе, обычно помечаются аннотацией @ExceptionHandler, которая указывает,
// какой тип исключения будет обрабатываться этим методом
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    // Обработчик для BadRequestException
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(BadRequestException ex) {

        log.error("Bad request exception", ex);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorDto.builder()
                        .error("Bad Request")
                        .errorDescription(ex.getMessage())
                        .build());
    }

    // Обработчик для NotFoundException
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex) {

        log.error("Not found exception", ex);

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorDto.builder()
                        .error("Not Found")
                        .errorDescription(ex.getMessage())
                        .build());
    }

    // Обработчик для всех других типов исключений
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleOtherExceptions(Exception ex) {

        log.error("Exception during execution of application", ex);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorDto.builder()
                        .error("Internal Server Error")
                        .errorDescription(ex.getMessage())
                        .build());
    }

}
