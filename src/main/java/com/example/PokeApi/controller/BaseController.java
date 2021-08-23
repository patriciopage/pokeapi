package com.example.PokeApi.controller;

import com.example.PokeApi.exception.ElementNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

public class BaseController {

    @ExceptionHandler
    public ResponseEntity<String> handleElementNotFoundException(ElementNotFoundException ex) {
        String errorMessage = String.format("Resource of type '%s' with id %s was not found", ex.getResourceType(), ex.getValue());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        String errorMessage = String.format("Invalid input %s for parameter %s", ex.getValue(), ex.getName());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }
}
