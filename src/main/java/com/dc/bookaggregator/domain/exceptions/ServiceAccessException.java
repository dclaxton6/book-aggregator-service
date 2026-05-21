package com.dc.bookaggregator.domain.exceptions;

public sealed class ServiceAccessException extends BookAggregatorException permits ResourceNotFoundException, PersistenceFailureException {
    public ServiceAccessException(String message) {
        super(message);
    }

    public ServiceAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}