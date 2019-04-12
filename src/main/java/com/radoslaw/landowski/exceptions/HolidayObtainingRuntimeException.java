package com.radoslaw.landowski.exceptions;

/**
 * The top level exception that all internal exceptions should derive from. This exception is globally handled by
 * Spring's ControllerAdvice annotated class called ExceptionHandlers. Thrown when external API throws it's own errors
 * due to timeouts, lack of authorization etc. The exception must extend RuntimeException as it is used in parallelStream
 */
public class HolidayObtainingRuntimeException extends RuntimeException {
    public HolidayObtainingRuntimeException(Throwable cause) {
        super(cause);
    }
}
