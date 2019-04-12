package com.radoslaw.landowski.validators;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

/**
 * Validates whether a given string is one of the IS0-3166 country codes
 */
@Component
public class ISOCountryCodeValidator implements ConstraintValidator<ISOCountryCode, String> {

    // !REVIEW: Intentional use of HashSet due to performance reasons.
    public static final HashSet<String> ISO_COUNTRY_CODES = new HashSet<>(Arrays.asList(Locale.getISOCountries()));

    @Override
    public void initialize(ISOCountryCode param) {}

    @Override
    public boolean isValid(String givenParam, ConstraintValidatorContext context) {
        return ISO_COUNTRY_CODES.contains(givenParam);
    }
}
