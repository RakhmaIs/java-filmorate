package ru.yandex.practicum.filmorate.exceptions;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;


@RestControllerAdvice("ru/yandex/practicum/filmorate/controller")
public class ErrorsHandler {
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleValidationExc(final ValidationException e) {
        return new ResponseEntity<>(
                e.getMessage(),
                e.getStatus());

    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> handleResponseStatusExc(final ResponseStatusException e) {
        return new ResponseEntity<>(
                e.getMessage(),
                e.getStatus());

    }
}

