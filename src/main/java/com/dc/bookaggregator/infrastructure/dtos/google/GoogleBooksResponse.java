package com.dc.bookaggregator.infrastructure.dtos.google;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GoogleBooksResponse(List<Volume> items) {
    public  GoogleBooksResponse {
            items = (items == null) ? List.of() : List.copyOf(items);
    }
}