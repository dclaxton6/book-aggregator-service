package com.dc.bookaggregator.infrastructure.clients.google.mapper;

import com.dc.bookaggregator.domain.exceptions.ResourceNotFoundException;
import com.dc.bookaggregator.domain.exceptions.SieveFailureException;
import com.dc.bookaggregator.domain.models.Book;
import com.dc.bookaggregator.infrastructure.dtos.google.GoogleBooksResponse;
import com.dc.bookaggregator.infrastructure.dtos.google.IndustryIdentifier;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class Mapper {
    public Book toDomain(GoogleBooksResponse response){
        var volume = response.items().stream().findFirst().orElseThrow(() ->
                new ResourceNotFoundException("No book volume payloads located in the external registry for the requested ISBN"));
        var info = volume.volumeInfo();
        String id = volume.id();
        String title = info.title();
        List<String> authors = normalizeAuthors(info.authors());
        String publisher = info.publisher();
        LocalDate publishedDate = parsePublishedDate(info.publishedDate());
        String description = info.description();
        String isbn13 = extractIsbn13(info.industryIdentifiers());
        int pageCount = info.pageCount();
        Double averageRating = info.averageRating();
        int ratingCount = info.ratingsCount();

        return new Book(id, title, authors, publisher, publishedDate,
                description, isbn13, pageCount, averageRating, ratingCount);
    }

    private List<String> normalizeAuthors(List<String> authors){return (authors == null) ? List.of() : authors;}

    private LocalDate parsePublishedDate(String rawDate){
        if(rawDate == null || rawDate.isEmpty()){return null;}
        try {
                if (rawDate.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
                    return LocalDate.parse(rawDate);
                }  else if (rawDate.matches("^\\d{4}-\\d{2}$")) {
                    return LocalDate.parse(rawDate + "-01");
                }  else if(rawDate.matches("^\\d{4}$")){
                    return LocalDate.parse(rawDate + "-01-01");
                } else{
                throw new SieveFailureException("Encountered corrupt or completely unparsable date format: " + rawDate );
            }
        } catch (DateTimeParseException ex){
           throw new SieveFailureException("Valid date layout contains an invalid calendar sequence: " + rawDate, ex);
        }
    }

    private String extractIsbn13(List<IndustryIdentifier> identifiers){
        var isbn_13 = identifiers.stream().filter(x -> x.type().equals("ISBN_13")).findFirst();
        if (isbn_13.isPresent()){
          return isbn_13.get().identifier();
        }
        else {
            var isbn_10 = identifiers.stream().filter(x -> x.type().equals("ISBN_10")).findFirst();
            if(isbn_10.isPresent()){
              return isbn_10.get().identifier();
            } else {
                return "";
            }
        }
    }
}