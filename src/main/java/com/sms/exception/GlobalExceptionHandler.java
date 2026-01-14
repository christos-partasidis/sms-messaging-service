package com.sms.exception;

import com.sms.dto.ErrorResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.util.List;
import java.util.stream.Collectors;

/** 
 * Global exception handler for the application.
 * 
 * Catches exceptions thrown anywhere in the application and converts
 * them to consistent, user-friendly error responses.
 * 
 * @Provider annotation registers this with JAX-RS automatically.
 */
@Provider
public class GlobalExceptionHandler implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {
        
        // Handle validation errors (from @Valid annotation)
        if (exception instanceof ConstraintViolationException) {
            return handleConstraintViolation((ConstraintViolationException) exception);
        }

        // Handle message not found
        if (exception instanceof MessageNotFoundException) {
            return handleMessageNotFound((MessageNotFoundException) exception);
        }

        // Handle invalid message (business rule violation)
        if (exception instanceof InvalidMessageException) {
            return handleInvalidMessage((InvalidMessageException) exception);
        }

        // Handle all other exceptions (unexpected errors)
        return handleGenericException(exception);
    }

    private Response handleConstraintViolation(ConstraintViolationException exception) {
        List<String> errors = exception.getConstraintViolations()
            .stream()
            .map(ConstraintViolation::getMessage)
            .collect(Collectors.toList());

        ErrorResponse errorResponse = new ErrorResponse(
            Response.Status.BAD_REQUEST.getStatusCode(),
            "Validation failed",
            errors
        );

        return Response.status(Response.Status.BAD_REQUEST)
            .entity(errorResponse)
            .build();
    }

    private Response handleMessageNotFound(MessageNotFoundException exception) {
        ErrorResponse errorResponse = new ErrorResponse(
            Response.Status.NOT_FOUND.getStatusCode(),
            exception.getMessage()
        );

        return Response.status(Response.Status.NOT_FOUND)
            .entity(errorResponse)
            .build();
    }

    private Response handleInvalidMessage(InvalidMessageException exception) {
        ErrorResponse errorResponse = new ErrorResponse(
            Response.Status.BAD_REQUEST.getStatusCode(),
            exception.getMessage()
        );

        return Response.status(Response.Status.BAD_REQUEST)
            .entity(errorResponse)
            .build();
    }

    private Response handleGenericException(Exception exception) {
        // Log the full exception for debugging (in production, use proper logging)
        exception.printStackTrace();

        ErrorResponse errorResponse = new ErrorResponse(
            Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
            "An unexpected error occurred. Please try again later."
        );

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
            .entity(errorResponse)
            .build();
    }
}