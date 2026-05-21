package com.dc.bookaggregator.domain.exceptions;

public abstract sealed class BookAggregatorException extends RuntimeException permits DataIntegrityException, ServiceAccessException  {
    public BookAggregatorException(String message) {
        super(message);
    }

    public BookAggregatorException(String message, Throwable cause) {
        super(message, cause);
    }
}