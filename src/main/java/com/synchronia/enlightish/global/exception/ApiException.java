package com.synchronia.enlightish.global.exception;

public class ApiException extends RuntimeException {

    private int statusCode;

    public ApiException(String message) {
        super(message);
        this.statusCode = 500;
    }

    public ApiException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

}
