package com.sms.exception;

/**
 * Exception thrown when message validation fails at the service layer.
 * 
 * Used for business rule violations that aren't covered by Bean Validation.
 * Example: source and destination numbers are the same.
 */
public class InvalidMessageException extends RuntimeException {

    public InvalidMessageException(String message) {
        super(message);
    }
}