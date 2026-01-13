package com.sms.model;

/**
 * MessageStatus class to represent the status of a message.
 */
public enum MessageStatus {
    
    /**
     * Message has been received and is waiting to be processed
     */
    PENDING,
    /**
     * Message has been successfully delivered to the recipient
     */
    DELIVERED,
    /**
     * Message delivery has failed
     */
    FAILED
}
