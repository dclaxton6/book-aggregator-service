package com.dc.bookaggregator.domain.models;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public record Book(String id,
                   String title,
                   List<String> authors,
                   String publisher,
                   LocalDate publishedDate,
                   String description,
                   String isbn,
                   int pageCount,
                   Double averageRating,
                   int ratingsCount) {
    public Book{
        Objects.requireNonNull(id,"id must not be null");
        Objects.requireNonNull(title,"Title must not be null");
        authors = (authors == null) ? List.of() : List.copyOf(authors);
    }
}