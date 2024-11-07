package com.synchronia.enlightish.presentation.exception;

public class EnlightishException extends Exception {

    private int statusCode;

    public EnlightishException(String message) {
        super(message);
        this.statusCode = 500;
    }

    public EnlightishException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    int getStatusCode() {
        return statusCode;
    }

}
