package com.sms.messaging;

/**
 * Event object sent to RabbitMQ for async processing.
 * 
 * This is a simple POJO that carries the message ID through the queue.
 * The consumer will fetch full message details from the database.
 */
public class SmsMessageEvent {

    private Long messageId;

    // Default constructor (required for JSON deserialization)
    public SmsMessageEvent() {
    }

    public SmsMessageEvent(Long messageId) {
        this.messageId = messageId;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    @Override
    public String toString() {
        return "SmsMessageEvent{messageId=" + messageId + "}";
    }
}