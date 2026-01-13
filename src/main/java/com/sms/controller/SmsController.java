package com.sms.controller;

import com.sms.dto.ErrorResponse;
import com.sms.dto.SmsRequest;
import com.sms.dto.SmsResponse;
import com.sms.service.SmsService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 * REST Controller for SMS operations.
 * 
 * This is the entry point for all HTTP requests related to SMS messaging.
 * The controller is thin - it only handles HTTP concerns and delegates
 * business logic to the service layer.
 * 
 * Annotations:
 * - @Path: Base URL path for all endpoints in this controller
 * - @Produces/@Consumes: Specifies JSON as the data format
 */
@Path("/api/sms")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SmsController {

    private final SmsService smsService;

    @Inject
    public SmsController(SmsService smsService) {
        this.smsService = smsService;
    }

    /**
     * Send a new SMS message.
     * 
     * POST /api/sms/send
     * 
     * @param request the SMS request body
     * @return 201 Created with message details, or 400 Bad Request if validation fails
     */
    @POST
    @Path("/send")
    public Response sendMessage(@Valid SmsRequest request) {
        SmsResponse response = smsService.sendMessage(request);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    /**
     * Get a message by ID.
     * 
     * GET /api/sms/{id}
     * 
     * @param id the message ID
     * @return 200 OK with message details, or 404 Not Found
     */
    @GET
    @Path("/{id}")
    public Response getMessageById(@PathParam("id") Long id) {
        return smsService.getMessageById(id)
            .map(response -> Response.ok(response).build())
            .orElseGet(() -> Response.status(Response.Status.NOT_FOUND)
                .entity(new ErrorResponse(404, "Message not found with id: " + id))
                .build());
    }

    /**
     * Get all messages for a phone number (as sender or recipient).
     * 
     * GET /api/sms/phone/{phoneNumber}
     * 
     * @param phoneNumber the phone number to search
     * @return 200 OK with list of messages
     */
    @GET
    @Path("/phone/{phoneNumber}")
    public Response getMessagesByPhoneNumber(@PathParam("phoneNumber") String phoneNumber) {
        List<SmsResponse> messages = smsService.getMessagesByPhoneNumber(phoneNumber);
        return Response.ok(messages).build();
    }

    /**
     * Get all messages sent from a specific number.
     * 
     * GET /api/sms/from/{sourceNumber}
     * 
     * @param sourceNumber the sender's phone number
     * @return 200 OK with list of messages
     */
    @GET
    @Path("/from/{sourceNumber}")
    public Response getMessagesBySourceNumber(@PathParam("sourceNumber") String sourceNumber) {
        List<SmsResponse> messages = smsService.getMessagesBySourceNumber(sourceNumber);
        return Response.ok(messages).build();
    }

    /**
     * Get all messages sent to a specific number.
     * 
     * GET /api/sms/to/{destinationNumber}
     * 
     * @param destinationNumber the recipient's phone number
     * @return 200 OK with list of messages
     */
    @GET
    @Path("/to/{destinationNumber}")
    public Response getMessagesByDestinationNumber(@PathParam("destinationNumber") String destinationNumber) {
        List<SmsResponse> messages = smsService.getMessagesByDestinationNumber(destinationNumber);
        return Response.ok(messages).build();
    }
}