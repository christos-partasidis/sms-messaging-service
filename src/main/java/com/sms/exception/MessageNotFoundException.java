package com.sms.exception;

/**
 * Exception thrown when a message is not found in the database.
 * 
 * This is a runtime exception (unchecked) because:
 * - It represents a recoverable client error (404)
 * - We don't want to force try-catch everywhere
 */
public class MessageNotFoundException extends RuntimeException {

    private final Long messageId;

    public MessageNotFoundException(Long messageId) {
        super("Message not found with id: " + messageId);
        this.messageId = messageId;
    }

    public Long getMessageId() {
        return messageId;
    }
}