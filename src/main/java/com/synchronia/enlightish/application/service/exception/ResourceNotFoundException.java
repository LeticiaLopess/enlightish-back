package com.synchronia.enlightish.application.service.exception;

public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(Object id) {
        super("Object not found. Id: " + id);
    }

}
