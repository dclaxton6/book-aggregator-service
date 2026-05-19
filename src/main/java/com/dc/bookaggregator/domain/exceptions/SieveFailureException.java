package com.dc.bookaggregator.domain.exceptions;

public final class SieveFailureException extends DataIntegrityException {
    public SieveFailureException(String message) {
        super(message);
    }

    public SieveFailureException(String message, Throwable cause) {
        super(message, cause);
    }
}