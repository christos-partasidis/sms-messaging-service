package com.sms.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

/**
 * JPA Entity representing an SMS Message.
 * 
 * Extends PanacheEntity which provides:
 * - Auto-generated Long id field
 * - Built in CRUD operations
 * - Active Record pattern for simpler database operations
 * @see <a href="https://quarkus.io/guides/hibernate-orm-panache">Quarkus Panache Guide</a>
 */
@Entity
@Table(name = "messages")
public class Message extends PanacheEntity {
    
    /**
     * The sender's phone number
     * Example: "+306912341524"
     */
    @Column(name = "source_number", nullable = false)
    public String sourceNumber;

    /**
     * The recipient's phone number
     * Example: "+306912341525"
     */
    @Column(name = "destination_number", nullable = false)
    public String destinationNumber;

    /**
     * The content of the SMS message
     * Example: "Hello, this is a test message."
     */
    @Column(name = "message_content", nullable = false, length = 160)
    public String content;

    /**
     * Current status of the message
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    public MessageStatus status;

    /**
     * Error message if delivery failed
     */
    @Column(name = "error_message")
    public String errorMessage;

    /**
     * Timestamp when the message was created
     */
    @Column(name = "created_at", nullable = false)
    public LocalDateTime createdAt;

    /**
     * Timestamp when the message was last updated
     */
    @Column(name = "updated_at", nullable = false)
    public LocalDateTime updatedAt;

    /**
     * Default constractor required by JPA 
     */
    public Message() {
    }

    /**
     * Creates a new message with PENDING status.
     * Automatically sets createdAt and UpdatedAt timestamps.
     * 
     * @param sourceNumber The sender's phone number
     * @param destinationNumber The recipient's phone number
     * @param content The content of the SMS message
     */

    public Message(String sourceNumber, String destinationNumber, String content) {
        this.sourceNumber = sourceNumber;
        this.destinationNumber = destinationNumber;
        this.content = content;
        this.status = MessageStatus.PENDING;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Marks the message as successfully delivered.
     */
    public void markAsDelivered() {
        this.status = MessageStatus.DELIVERED;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Marks the message as failed with an error message.
     *
     * @param errorMessage description of why delivery failed
     */
    public void markAsFailed(String errorMessage) {
        this.status = MessageStatus.FAILED;
        this.errorMessage = errorMessage;
        this.updatedAt = LocalDateTime.now();
    }

}
