package com.dc.bookaggregator.domain.exceptions;

public final class PersistenceFailureException extends ServiceAccessException {
    public PersistenceFailureException(String message) {
        super(message);
    }

    public PersistenceFailureException(String message, Throwable cause) {
        super(message, cause);
    }
}