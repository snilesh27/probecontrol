package com.maveric.systems.probecontrol.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidCommandException.class)
    public ResponseEntity<String> handleInvalidCommand(InvalidCommandException ex) {
        return ResponseEntity
                .badRequest()
                .body(ex.getMessage());
    }
}
