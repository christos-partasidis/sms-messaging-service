package com.sms.messaging;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.jboss.logging.Logger;

/**
 * Producer that sends SMS messages to RabbitMQ for async processing.
 * 
 * Uses SmallRye Reactive Messaging to interact with RabbitMQ.
 * Messages are sent to the "sms-outgoing" channel defined in application.properties.
 */
@ApplicationScoped
public class SmsMessageProducer {

    private static final Logger LOG = Logger.getLogger(SmsMessageProducer.class);

    @Inject
    @Channel("sms-outgoing")
    Emitter<SmsMessageEvent> emitter;

    /**
     * Send a message to the queue for async processing.
     * 
     * @param messageId the ID of the message to process
     */
    public void sendToQueue(Long messageId) {
        SmsMessageEvent event = new SmsMessageEvent(messageId);
        
        LOG.infof("Sending message to queue: %s", event);
        
        emitter.send(event);
        
        LOG.infof("Message sent to queue successfully: messageId=%d", messageId);
    }
}