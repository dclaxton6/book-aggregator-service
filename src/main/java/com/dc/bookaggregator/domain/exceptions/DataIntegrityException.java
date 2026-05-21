package com.dc.bookaggregator.domain.exceptions;

public sealed class DataIntegrityException extends BookAggregatorException permits ISBNResolutionException, SieveFailureException {
    public DataIntegrityException(String message) {
        super(message);
    }

    public DataIntegrityException(String message, Throwable cause) {
        super(message, cause);
    }
}