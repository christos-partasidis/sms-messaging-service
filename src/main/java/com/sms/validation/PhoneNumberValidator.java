package com.sms.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * Validator implementation for @ValidPhoneNumber annotation.
 * 
 * Phone number rules:
 * - Optional + prefix
 * - 7 to 15 digits (E.164 standard allows up to 15)
 * - No spaces, dashes, or other characters
 * 
 * E.164 is the international telephone numbering standard.
 * 
 * @see <a href="https://en.wikipedia.org/wiki/E.164">E.164 Standard</a>
 */
public class PhoneNumberValidator implements ConstraintValidator<ValidPhoneNumber, String> {

    /**
     * Regex pattern for phone number validation.
     * 
     * ^\\+?     - Optional + at the start
     * [1-9]    - First digit must be 1-9 (no leading zeros)
     * \\d{6,14} - Followed by 6-14 more digits (total 7-15 digits)
     * $        - End of string
     */
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+?[1-9]\\d{6,14}$");

    @Override
    public void initialize(ValidPhoneNumber constraintAnnotation) {
        // No initialization needed
    }

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
        // Null values are handled by @NotBlank
        if (phoneNumber == null || phoneNumber.isBlank()) {
            return true;
        }

        return PHONE_PATTERN.matcher(phoneNumber).matches();
    }
}