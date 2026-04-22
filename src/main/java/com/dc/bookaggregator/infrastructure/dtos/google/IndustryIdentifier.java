package com.dc.bookaggregator.infrastructure.dtos.google;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public record IndustryIdentifier(String type, String identifier) {

    public IndustryIdentifier{
        Objects.requireNonNull(type, "type must not be null");
        Objects.requireNonNull(identifier, "identifier must not be null");
    }
}