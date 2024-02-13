package com.example.quiznew.api.exceptions;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
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

    @ExceptionHandler(Exception.class)
    // В данном случае перехватываются все подряд
    public ResponseEntity<Object> exception(Exception ex, WebRequest request) throws Exception {
        // Метод обработки исключений

        log.error("Exception during execution of application", ex);
        // Информация об исключении с уровнем ERROR записывается в логгер с сообщением

        return handleException(ex, request);
        // Этот метод генерирует ResponseEntity<Object>, внутри него создается HttpHeaders headers,
        // куда добавляется информация о том какая именно ошибка произошла
    }

}
