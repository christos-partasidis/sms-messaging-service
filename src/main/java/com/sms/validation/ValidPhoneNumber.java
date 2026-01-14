package com.sms.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom validation annotation for phone numbers.
 * 
 * Validates that a string is a valid phone number format.
 * Supports international format with optional + prefix.
 * 
 * Usage:
 * @ValidPhoneNumber
 * private String phoneNumber;
 * 
 * Valid examples: "+306912345678", "306912345678", "+1234567890"
 * Invalid examples: "abc", "123", "+30-691-234-5678"
 */
@Documented
@Constraint(validatedBy = PhoneNumberValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPhoneNumber {

    String message() default "Invalid phone number format. Use international format (e.g., +306912345678)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}