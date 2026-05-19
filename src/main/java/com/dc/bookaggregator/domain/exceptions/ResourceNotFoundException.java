package com.dc.bookaggregator.domain.exceptions;

public final class ResourceNotFoundException extends ServiceAccessException {
    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}