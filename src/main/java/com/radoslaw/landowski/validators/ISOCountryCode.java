package com.radoslaw.landowski.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ISOCountryCodeValidator.class)
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ISOCountryCode {

    String message() default "Incorrect Country Code!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}