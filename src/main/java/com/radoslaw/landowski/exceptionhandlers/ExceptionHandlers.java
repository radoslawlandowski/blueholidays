package com.radoslaw.landowski.exceptionhandlers;

import com.radoslaw.landowski.exceptions.HolidayObtainingException;
import com.radoslaw.landowski.model.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;

/**
 * Class contains exception handlers for input parameters existence and validation but also inner exception.
 * Due to simplicty of these handlers they've been all put in the same class.
 */
@ControllerAdvice
public class ExceptionHandlers {
    private final static Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlers.class);

    public final static String CONSTRAINT_VILOATION_DESCRIPTION = "Constraint violations!";
    public final static String CONVERSION_FAILED_DESCRIPTION = "Conversion Failure!";
    public final static String INTERNAL_SERVER_ERROR_DESCRIPTION = "Internal Server Error!";
    public final static String MISSING_PARAMETER_DESCRIPTION = "Missing mandatory parameters!";

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException exception) {
        ErrorResponse response = ErrorResponse.builder().description(CONSTRAINT_VILOATION_DESCRIPTION).build();
        exception.getConstraintViolations().forEach(conVio -> response.addProblem(conVio.getInvalidValue().toString(), conVio.getMessage()));

        log(exception);

        return new ResponseEntity<>(response, null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConversionFailedException.class)
    public ResponseEntity<ErrorResponse> handleConversionFailedException(ConversionFailedException exception) {
        ErrorResponse response = ErrorResponse.builder().description(CONVERSION_FAILED_DESCRIPTION).build();
        response.addProblem(exception.getValue().toString(), exception.getCause().getLocalizedMessage());

        log(exception);

        return new ResponseEntity<>(response, null, HttpStatus.BAD_REQUEST);
    }

    /**
     * @param exception
     * @return general error message that is returned to the user. Protects from leaking sensitive stack traces to users
     */
    @ExceptionHandler(HolidayObtainingException.class)
    public ResponseEntity<ErrorResponse> handleBlueHolidaysException(HolidayObtainingException exception) {
        ErrorResponse response = ErrorResponse.builder().description(INTERNAL_SERVER_ERROR_DESCRIPTION).build();

        log(exception);

        return new ResponseEntity<>(response, null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(MissingServletRequestParameterException exception) {
        ErrorResponse response = ErrorResponse.builder().description(MISSING_PARAMETER_DESCRIPTION).build();
        response.addProblem(exception.getParameterName(), exception.getMessage());

        log(exception);

        return new ResponseEntity<>(response, null, HttpStatus.BAD_REQUEST);
    }

    private void log(Throwable exception) {
        LOGGER.info("Encountered {}", exception.getClass(), exception);
    }
}