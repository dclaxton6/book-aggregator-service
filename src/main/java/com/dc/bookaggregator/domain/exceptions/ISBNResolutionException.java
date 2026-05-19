package com.dc.bookaggregator.domain.exceptions;

public final class ISBNResolutionException extends DataIntegrityException {
    public ISBNResolutionException(String message) {
        super(message);
    }
    public ISBNResolutionException(String message, Throwable cause) {
        super(message, cause);
    }
}