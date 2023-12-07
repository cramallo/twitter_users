package com.twitter.users.infraestructure.exceptions;

import com.twitter.users.infraestructure.repositories.exceptions.EntityNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(final EntityNotFoundException exception) {
        final var errorResponse = ErrorResponse.builder()
                .message(exception.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .build();
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), errorResponse.getStatus());
    }

}
