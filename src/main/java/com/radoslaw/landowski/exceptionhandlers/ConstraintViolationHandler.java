package com.radoslaw.landowski.exceptionhandlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;

@ControllerAdvice
public class ConstraintViolationHandler {

    @ExceptionHandler
    public ResponseEntity<String> handle(ConstraintViolationException exception) {
        String errorMessage = new ArrayList<>(exception.getConstraintViolations()).get(0).getMessage();
        return new ResponseEntity<>(errorMessage, null, HttpStatus.BAD_REQUEST);
    }
}