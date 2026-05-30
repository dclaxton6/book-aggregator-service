package com.dc.bookaggregator.infrastructure.services;

import com.dc.bookaggregator.domain.exceptions.*;
import com.dc.bookaggregator.domain.models.Book;
import com.dc.bookaggregator.infrastructure.clients.google.GoogleBooksClient;
import com.dc.bookaggregator.infrastructure.clients.google.mapper.Mapper;
import com.dc.bookaggregator.infrastructure.dtos.google.GoogleBooksResponse;
import com.dc.bookaggregator.infrastructure.dtos.google.Volume;
import com.dc.bookaggregator.infrastructure.dtos.google.VolumeInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookAggregatorServiceImplTests {

    @Mock
    private Mapper mapper;
    @Mock
    private GoogleBooksClient client;
    @InjectMocks
    private  BookAggregatorServiceImpl bookAggregatorService;

    @Test
    public void testAggregateBooksShouldReturnBook(){
        List<Volume> fakeItems = new ArrayList<>();
        fakeItems.add(new Volume("test1", new VolumeInfo("",List.of(),"","",
                "",List.of(),2,9.4, 30)));
        GoogleBooksResponse fakeResponse = new GoogleBooksResponse(fakeItems);
        when(mapper.toDomain(any())).thenReturn(new Book("","", List.of(), "", LocalDate.EPOCH,
                "","",3,6.6, 3));
        when(client.callGoogleBooksApi(anyString())).thenReturn(CompletableFuture.completedFuture(fakeResponse));
        String isbn = "978-0-061-96436-7";
        Book book = bookAggregatorService.aggregateBook(isbn);
        assertNotNull(book);
    }

    @Test
    public void testAggregateBooksShouldThrowISBNResolutionException(){
        when(client.callGoogleBooksApi(anyString()))
                .thenReturn(CompletableFuture.failedFuture(new ISBNResolutionException("")));
       String isbn = "978-0-061-96436-7";
        assertThrows(ISBNResolutionException.class, () -> bookAggregatorService.aggregateBook(isbn));
    }

    @Test
    public void testAggregateBooksShouldThrowSieveFailureException(){
        when(client.callGoogleBooksApi(anyString()))
                .thenReturn(CompletableFuture.failedFuture(new SieveFailureException("")));
        String isbn = "978-0-061-96436-7";
        assertThrows(SieveFailureException.class, () -> bookAggregatorService.aggregateBook(isbn));
    }

    @Test
    public void testAggregateBooksShouldThrowResourceNotFoundException(){
        when(client.callGoogleBooksApi(anyString()))
                .thenReturn(CompletableFuture.failedFuture(new ResourceNotFoundException("")));
        String isbn = "978-0-061-96436-7";
        assertThrows(ResourceNotFoundException.class, () -> bookAggregatorService.aggregateBook(isbn));
    }

    @Test
    public void testAggregateBooksShouldThrowPersistenceFailureException(){
        when(client.callGoogleBooksApi(anyString()))
                .thenReturn(CompletableFuture.failedFuture(new PersistenceFailureException("")));
        String isbn = "978-0-061-96436-7";
        assertThrows(PersistenceFailureException.class, () -> bookAggregatorService.aggregateBook(isbn));
    }

    @Test
    public void testAggregateBooksShouldThrowBookAggregatorException(){
        when(client.callGoogleBooksApi(anyString()))
                .thenReturn(CompletableFuture.failedFuture(new TestBookAggregatorException("")));
        String isbn = "978-0-061-96436-7";
        assertThrows(TestBookAggregatorException.class, () -> bookAggregatorService.aggregateBook(isbn));
    }

    @Test
    public void testNotInstanceOfBAE(){
        when(client.callGoogleBooksApi(anyString()))
                .thenReturn(CompletableFuture.failedFuture(new IOException("")));
        String isbn = "978-0-061-96436-7";
        CompletionException exception =
                assertThrows(CompletionException.class, () -> bookAggregatorService.aggregateBook(isbn));
        assertEquals(IOException.class, exception.getCause().getClass());
    }
}