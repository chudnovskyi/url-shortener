package de.objego.urlshortener.exception;

import de.objego.urlshortener.dto.response.ErrorResponse;
import de.objego.urlshortener.exception.url.EncodedUrlNotFoundException;
import de.objego.urlshortener.exception.url.UrlEncodingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * Global exception handler for handling specific exceptions and generating error responses.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles the MethodArgumentNotValidException and generates a response with BAD_REQUEST status
     * containing validation errors.
     *
     * @param e The MethodArgumentNotValidException to be handled.
     * @return A ResponseEntity containing the validation error response with BAD_REQUEST status.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(errors, BAD_REQUEST);
    }

    /**
     * Handles the EncodedUrlNotFoundException and generates an error response with NOT_FOUND status.
     *
     * @param e The EncodedUrlNotFoundException to be handled.
     * @return A ResponseEntity containing the error response with NOT_FOUND status.
     */
    @ExceptionHandler({
            EncodedUrlNotFoundException.class
    })
    public ResponseEntity<ErrorResponse> handleNotFound(Exception e) {
        return generateDefaultErrorMessage(e, NOT_FOUND);
    }

    /**
     * Handles the UrlEncodingException and generates an error response with BAD_REQUEST status.
     *
     * @param e The UrlEncodingException to be handled.
     * @return A ResponseEntity containing the error response with BAD_REQUEST status.
     */
    @ExceptionHandler({
            UrlEncodingException.class
    })
    public ResponseEntity<ErrorResponse> handleBadRequest(Exception e) {
        return generateDefaultErrorMessage(e, BAD_REQUEST);
    }

    /**
     * Generates a default error response with the provided status and exception details.
     *
     * @param e          The exception to be included in the error response.
     * @param httpStatus The HTTP status code to be included in the error response.
     * @return A ResponseEntity containing the error response with the specified status.
     */
    private ResponseEntity<ErrorResponse> generateDefaultErrorMessage(Exception e, HttpStatus httpStatus) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(e.getMessage())
                .code(httpStatus.value())
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(errorResponse, httpStatus);
    }
}
