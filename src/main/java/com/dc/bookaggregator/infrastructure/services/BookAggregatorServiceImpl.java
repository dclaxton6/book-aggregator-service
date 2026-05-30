package com.dc.bookaggregator.infrastructure.services;

import com.dc.bookaggregator.domain.exceptions.*;
import com.dc.bookaggregator.domain.models.Book;
import com.dc.bookaggregator.domain.services.BookAggregatorService;
import com.dc.bookaggregator.infrastructure.clients.google.GoogleBooksClient;
import com.dc.bookaggregator.infrastructure.clients.google.mapper.Mapper;

import java.util.concurrent.CompletionException;

public class BookAggregatorServiceImpl implements BookAggregatorService {

    private final GoogleBooksClient client;
    private final Mapper mapper;

    public BookAggregatorServiceImpl(GoogleBooksClient client, Mapper mapper) {
        this.client = client;
        this.mapper = mapper;
    }

    @Override
    public Book aggregateBook(String isbn) {
        try{
            return client.callGoogleBooksApi(isbn).thenApply(mapper::toDomain).join();
        }
        catch (CompletionException e){
            if(e.getCause() instanceof  BookAggregatorException bae) {
                throw handleBookAggregatorException(bae);
            }
            throw e;
        }
    }

    private BookAggregatorException handleBookAggregatorException(BookAggregatorException bae){
        return switch (bae){
            case ISBNResolutionException ire ->  ire;
            case SieveFailureException sieve ->   sieve;
            case ResourceNotFoundException rne ->  rne;
            case PersistenceFailureException pfe ->  pfe;
            case BookAggregatorException base ->   base;
        };
    }
}