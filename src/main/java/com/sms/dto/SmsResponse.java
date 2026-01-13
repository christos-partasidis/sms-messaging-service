package com.sms.dto;

import com.sms.model.Message;
import com.sms.model.MessageStatus;
import java.time.LocalDateTime;

/**
 * DTO for SMS responses returned to clients.
 * 
 * Contains all information a client needs to know about their message.
 * Created from a Message entity using the static factory method.
 */
public class SmsResponse {
    private Long id;
    private String sourceNumber;
    private String destinationNumber;
    private String content;
    private MessageStatus status;
    private String errorMessage;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Default constructor
    public SmsResponse() {
    }

    // All-args constructor
    public SmsResponse(Long id, String sourceNumber, String destinationNumber, 
                       String content, MessageStatus status, String errorMessage,
                       LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.sourceNumber = sourceNumber;
        this.destinationNumber = destinationNumber;
        this.content = content;
        this.status = status;
        this.errorMessage = errorMessage;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * Factory method to create SmsResponse from Message entity.
     * 
     * This keeps the conversion logic in one place and makes
     * the code cleaner in service/controller layers.
     * 
     * @param message the entity to convert
     * @return SmsResponse DTO
     */
    public static SmsResponse fromEntity(Message message) {
        return new SmsResponse(
            message.id,
            message.sourceNumber,
            message.destinationNumber,
            message.content,
            message.status,
            message.errorMessage,
            message.createdAt,
            message.updatedAt
        );
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSourceNumber() {
        return sourceNumber;
    }

    public void setSourceNumber(String sourceNumber) {
        this.sourceNumber = sourceNumber;
    }

    public String getDestinationNumber() {
        return destinationNumber;
    }

    public void setDestinationNumber(String destinationNumber) {
        this.destinationNumber = destinationNumber;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MessageStatus getStatus() {
        return status;
    }

    public void setStatus(MessageStatus status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

}
