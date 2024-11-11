package com.synchronia.enlightish.global.exception;

import com.synchronia.enlightish.application.service.exception.DatabaseException;
import com.synchronia.enlightish.application.service.exception.ResourceNotFoundException;
import com.synchronia.enlightish.global.response.ErrorResponse;
import com.synchronia.enlightish.presentation.exception.EnlightishException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(ApiException exception, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                exception.getStatusCode(),
                HttpStatus.valueOf(exception.getStatusCode()).getReasonPhrase(),
                exception.getMessage(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(exception.getStatusCode()));
    }

    @ExceptionHandler(EnlightishException.class)
    public ResponseEntity<ErrorResponse> handleEnlightishException(ApiException exception, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                exception.getStatusCode(),
                HttpStatus.valueOf(exception.getStatusCode()).getReasonPhrase(),
                exception.getMessage(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(exception.getStatusCode()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException exception, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                404,
                "Not Found",
                exception.getMessage(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<ErrorResponse> handleDatabaseException(DatabaseException exception, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                500,
                "Internal Server Error",
                "Database error: " + exception.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException exception, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                500,
                "Internal Server Error",
                "A runtime exception occurred: " + exception.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception exception, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                500,
                "Internal Server Error",
                "An unexpected error occurred: " + exception.getMessage(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
