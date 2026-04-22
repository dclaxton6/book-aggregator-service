package com.dc.bookaggregator.infrastructure.dtos.google;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public record VolumeInfo(
        String title,
        List<String> authors,
        String publisher,
        String publishedDate,
        String description,
        List<IndustryIdentifier> industryIdentifiers,
        int pageCount,
        Double averageRating,
        int ratingsCount) {

    public VolumeInfo {
        Objects.requireNonNull(title);
        authors = (authors == null) ? List.of() : List.copyOf(authors);
        industryIdentifiers = (industryIdentifiers == null) ? List.of() : List.copyOf(industryIdentifiers);
    }

}