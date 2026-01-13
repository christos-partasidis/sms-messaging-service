package com.sms.service;

import com.sms.dto.SmsRequest;
import com.sms.dto.SmsResponse;
import com.sms.model.Message;
import com.sms.repository.MessageRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of SmsService.
 * 
 * Contains all business logic for SMS operations.
 * 
 * Annotations explained:
 * - @ApplicationScoped: Single instance shared across the application
 * - @Transactional: Database operations are wrapped in a transaction
 */
@ApplicationScoped
public class SmsServiceImpl implements SmsService {

    private final MessageRepository messageRepository;

    /**
     * Constructor injection (preferred over field injection).
     * 
     * Why constructor injection?
     * - Makes dependencies explicit
     * - Easier to test (can pass mock repository)
     * - Ensures object is fully initialized
     */
    @Inject
    public SmsServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    @Transactional
    public SmsResponse sendMessage(SmsRequest request) {
        // 1. Create Message entity from request
        Message message = new Message(
            request.getSourceNumber(),
            request.getDestinationNumber(),
            request.getContent()
        );

        // 2. Persist to database
        messageRepository.persist(message);

        // 3. TODO: Send to RabbitMQ for async processing
        // This will be implemented in the messaging layer

        // 4. Return response
        return SmsResponse.fromEntity(message);
    }

    @Override
    public Optional<SmsResponse> getMessageById(Long id) {
        return messageRepository.findByIdOptional(id)
            .map(SmsResponse::fromEntity);
    }

    @Override
    public List<SmsResponse> getMessagesByPhoneNumber(String phoneNumber) {
        // Get messages where phone is sender OR recipient
        List<Message> sentMessages = messageRepository.findBySourceNumber(phoneNumber);
        List<Message> receivedMessages = messageRepository.findByDestinationNumber(phoneNumber);

        // Combine and convert to responses
        sentMessages.addAll(receivedMessages);
        
        return sentMessages.stream()
            .map(SmsResponse::fromEntity)
            .collect(Collectors.toList());
    }

    @Override
    public List<SmsResponse> getMessagesBySourceNumber(String sourceNumber) {
        return messageRepository.findBySourceNumber(sourceNumber)
            .stream()
            .map(SmsResponse::fromEntity)
            .collect(Collectors.toList());
    }

    @Override
    public List<SmsResponse> getMessagesByDestinationNumber(String destinationNumber) {
        return messageRepository.findByDestinationNumber(destinationNumber)
            .stream()
            .map(SmsResponse::fromEntity)
            .collect(Collectors.toList());
    }
}