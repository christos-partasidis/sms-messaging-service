package com.sms.service;

import com.sms.dto.SmsRequest;
import com.sms.dto.SmsResponse;
import java.util.List;
import java.util.Optional;

/**
 * Service interface for SMS operations.
 * 
 * Defines the business operations available for SMS messaging.
 * Implementation contains the actual business logic.
 * 
 * Why an interface?
 * - Allows multiple implementations
 * - Defines clewar contract for what operations are avaiable
 * 
 */

public interface SmsService {
    /**
     * Send a new SMS message
     * 
     * This method:
     * 1. Validates the request
     * 2. Creates and persists a Message entity
     * 3. Queues the message for astync processing (RabbitMQ)
     * 4. Returns the created message details
     * 
     * @param request The SMS request containing source, destination, and content
     * @return SmsResponse with message details and PENDING status
     * 
     */
    SmsResponse sendMessage(SmsRequest request);

    /**
     * Get a message by its ID.
     * 
     * @param id the message ID
     * @return Optional containing SmsResponse if found, empty otherwise
     */
    Optional<SmsResponse> getMessageById(Long id);

    /**
     * Get all messages for a specific phone number.
     * 
     * Returns messages where the phone number is either sender or recipient.
     * 
     * @param phoneNumber the phone number to search for
     * @return list of messages involving this phone number
     */
    List<SmsResponse> getMessagesByPhoneNumber(String phoneNumber);

    /**
     * Get all messages sent from a specific number.
     * 
     * @param sourceNumber the sender's phone number
     * @return list of messages from this source
     */
    List<SmsResponse> getMessagesBySourceNumber(String sourceNumber);

    /**
     * Get all messages sent to a specific number.
     * 
     * @param destinationNumber the recipient's phone number
     * @return list of messages to this destination
     */
    List<SmsResponse> getMessagesByDestinationNumber(String destinationNumber);
}   