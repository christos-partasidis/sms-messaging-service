package com.sms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO for incoming SMS send requests.
 * 
 * This class contains validation annotations that provide
 * user-friendly error messages when validation fails.
 * 
 * Validation happens automatically when used with @Valid in controller.
 */

public class SmsRequest{

    /**
     * The sender's phone number.
     * Must be a valid phone number format.
     */
    @NotBlank(message = "Source number is required")
    private String sourceNumber;

    /**
     * The recipient's phone number.
     * Must be a valid phone number format.
     */
    @NotBlank(message = "Destination number is required")
    private String destinationNumber;

    /**
     * The SMS message content.
     * Standard SMS is limited to 160 characters.
     */
    @NotBlank(message = "Message content is required")
    @Size(max = 160, message = "Message content cannot exceed 160 characters")
    private String content;

    // Default constructor (required for JSON deserialization)
    public SmsRequest() {
    }

    // Constructor with all fields
    public SmsRequest(String sourceNumber, String destinationNumber, String content) {
        this.sourceNumber = sourceNumber;
        this.destinationNumber = destinationNumber;
        this.content = content;
    }

    // Getters and Setters
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

}


