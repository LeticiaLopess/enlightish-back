package com.synchronia.enlightish.presentation.exception;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.synchronia.enlightish.application.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestControllerAdvice
public class CustomExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @ExceptionHandler(UnrecognizedPropertyException.class)
    public ResponseEntity<String> handleInvalidPropertyException(UnrecognizedPropertyException exception) {
        String unknownField = exception.getPropertyName();

        LOGGER.error("O campo '{}' não é reconhecido. Verifique os campos disponíveis.", unknownField);

        String message = String.format("O campo '%s' não é reconhecido. Verifique os campos disponíveis.", unknownField);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

}
