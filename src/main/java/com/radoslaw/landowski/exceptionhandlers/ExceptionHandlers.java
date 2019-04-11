package com.radoslaw.landowski.exceptionhandlers;

import com.radoslaw.landowski.model.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class ExceptionHandlers {

    private final static Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlers.class);

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException exception) {
        ErrorResponse response = new ErrorResponse();
        response.setDescription("Constraint violations!");
        exception.getConstraintViolations().forEach(conVio -> response.addProblem(conVio.getInvalidValue().toString(), conVio.getMessage()));

        log(exception);

        return new ResponseEntity<>(response, null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConversionFailedException.class)
    public ResponseEntity<ErrorResponse> handleConversionFailedException(ConversionFailedException exception) {
        ErrorResponse response = new ErrorResponse();
        response.setDescription("Conversion Failure!");
        response.addProblem(exception.getValue().toString(), exception.getCause().getLocalizedMessage());

        log(exception);

        return new ResponseEntity<>(response, null, HttpStatus.BAD_REQUEST);
    }

    private void log(Throwable exception) {
        LOGGER.info("Encountered {}", exception.getClass(), exception);
    }
}