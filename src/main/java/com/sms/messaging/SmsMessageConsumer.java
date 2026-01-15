package com.sms.messaging;

import com.sms.model.Message;
import com.sms.repository.MessageRepository;
import io.smallrye.common.annotation.Blocking;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

import java.util.Random;

/**
 * Consumer that processes SMS messages from RabbitMQ.
 * 
 * Simulates SMS delivery with random success/failure.
 * Updates message status in the database after processing.
 */
@ApplicationScoped
public class SmsMessageConsumer {

    private static final Logger LOG = Logger.getLogger(SmsMessageConsumer.class);
    
    /**
     * Success rate for simulated delivery (80% success).
     */
    private static final double SUCCESS_RATE = 0.8;
    
    private final Random random = new Random();

    @Inject
    MessageRepository messageRepository;

    /**
     * Process incoming messages from the queue.
     * 
     * @Incoming: Listens to "sms-incoming" channel from application.properties
     * @Blocking: Allows blocking operations (database access)
     * @Transactional: Wraps in database transaction
     * 
     * @param event the message event from the queue
     */
    @Incoming("sms-incoming")
    @Blocking
    @Transactional
    public void processMessage(JsonObject json) {
        LOG.infof("Received message from queue: %s", json);

        // Extract messageId from JSON
        Long messageId = json.getLong("messageId");

        if (messageId == null) {
            LOG.warn("Received message without messageId, skipping");
            return;
        }
        
        // Find the message in database
        Message message = messageRepository.findById(messageId);
        
        if (message == null) {
            LOG.warnf("Message not found in database: messageId=%d", messageId);
            return;
        }

        // Simulate processing delay (like real SMS gateway)
        simulateProcessingDelay();

        // Simulate delivery result
        if (isDeliverySuccessful()) {
            message.markAsDelivered();
            LOG.infof("Message delivered successfully: messageId=%d", message.id);
        } else {
            String errorMessage = generateRandomError();
            message.markAsFailed(errorMessage);
            LOG.warnf("Message delivery failed: messageId=%d, error=%s", message.id, errorMessage);
        }

        // Update in database (handled by @Transactional)
        messageRepository.persist(message);
    }

    /**
     * Simulate network delay (100-500ms).
     */
    private void simulateProcessingDelay() {
        try {
            int delay = 100 + random.nextInt(400);
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Determine if delivery is successful based on SUCCESS_RATE.
     */
    private boolean isDeliverySuccessful() {
        return random.nextDouble() < SUCCESS_RATE;
    }

    /**
     * Generate a random error message for failed deliveries.
     */
    private String generateRandomError() {
        String[] errors = {
            "Destination number not reachable",
            "Network timeout",
            "Invalid destination number",
            "Carrier rejected message",
            "Insufficient balance",
            "Message blocked by carrier"
        };
        return errors[random.nextInt(errors.length)];
    }
}