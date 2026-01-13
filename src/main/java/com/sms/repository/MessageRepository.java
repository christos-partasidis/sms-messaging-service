package com.sms.repository;

import com.sms.model.Message;
import com.sms.model.MessageStatus;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

/**
 * Repository for Message entity database operations.
 * 
 * Uses PanacheRepository pattern which provides:
 * - Standard CRUID operations (persist, delete, findById, listAll)
 * - Custom query methods using Panache query language
 * 
 * PanacheRepository vs PanacheEntity:
 * - PanacheEntity: Active Record pattern (Message.findById())
 * - PanacheRepository: Repository pattern (messageRepository.findById())
 * 
 * @see <a href="https://quarkus.io/guides/hibernate-orm-panache#solution-2-using-the-repository-pattern">Panache Repository Guide</a>
 */
@ApplicationScoped
public class MessageRepository implements PanacheRepository<Message>{
    /**
     * Find all messages sent from a specific phone number.
     * @param sourceNumber the sender's phone number
     * @return list of messages from this source
     */
    public List<Message> findBySourceNumber(String sourceNumber) {
        return list("sourceNumber", sourceNumber);
    }

    /**
     * Find all messages sent to a specific phone number.
     * @param destinationNumber the recipient's phone number
     */
    public List<Message> findByDestinationNumber(String destinationNumber) {
        return list("destinationNumber", destinationNumber);
    }

    /**
     * Find all messages with a specific status.
     * @param status the message status
     * @return list of messages with this status
     */
    public List<Message> findByStatus(MessageStatus status) {
        return list("status", status);
    }

    /**
     * Count messages by status
     * @param status the status to count
     * @return number of messages with this status  
    */
    public long countByStatus(MessageStatus status) {
        return count("status", status);
    }

}