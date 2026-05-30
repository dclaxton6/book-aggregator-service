package com.dc.bookaggregator.infrastructure.clients.google.mapper;

import com.dc.bookaggregator.domain.exceptions.SieveFailureException;
import com.dc.bookaggregator.domain.models.Book;
import com.dc.bookaggregator.infrastructure.dtos.google.GoogleBooksResponse;
import com.dc.bookaggregator.infrastructure.dtos.google.IndustryIdentifier;
import com.dc.bookaggregator.infrastructure.dtos.google.Volume;
import com.dc.bookaggregator.infrastructure.dtos.google.VolumeInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MapperTest {
    Mapper mapper;
    @BeforeEach
    public void setup(){
        mapper = new Mapper();
    }

    @Test
    public void ParsePublishedDateShouldReturnFullDate(){
        String rawDate = "2025-10-10";
        List<Volume> fakeItems = new ArrayList<>();
        fakeItems.add(new Volume("test1", new VolumeInfo("",List.of(),"",rawDate,
                "",List.of(),2,9.4, 30)));
        GoogleBooksResponse fakeResponse = new GoogleBooksResponse(fakeItems);
        Book book = mapper.toDomain(fakeResponse);
        assertEquals(LocalDate.of(2025,10,10), book.publishedDate());
    }

    @Test
    public void ParsePublishedDateShouldReturnFullInvalidDate(){
        String rawDate = "2025-75-77";
        List<Volume> fakeItems = new ArrayList<>();
        fakeItems.add(new Volume("test1", new VolumeInfo("",List.of(),"",rawDate,
                "",List.of(),2,9.4, 30)));
        GoogleBooksResponse fakeResponse = new GoogleBooksResponse(fakeItems);
        SieveFailureException exception = assertThrows(SieveFailureException.class, () -> mapper.toDomain(fakeResponse));
        assertEquals("Valid date layout contains an invalid calendar sequence: " + rawDate, exception.getMessage());
    }

    @Test
    public void ParsePublishedDateYearAndMonthShouldReturnFullDate(){
        String rawDate = "2025-10";
        List<Volume> fakeItems = new ArrayList<>();
        fakeItems.add(new Volume("test1", new VolumeInfo("",List.of(),"",rawDate,
                "",List.of(),2,9.4, 30)));
        GoogleBooksResponse fakeResponse = new GoogleBooksResponse(fakeItems);
        Book book = mapper.toDomain(fakeResponse);
        assertEquals(LocalDate.of(2025,10,1), book.publishedDate());
    }

    @Test
    public void ParsePublishedDateOnlyYearShouldReturnFullDate(){
        String rawDate = "2025";
        List<Volume> fakeItems = new ArrayList<>();
        fakeItems.add(new Volume("test1", new VolumeInfo("",List.of(),"",rawDate,
                "",List.of(),2,9.4, 30)));
        GoogleBooksResponse fakeResponse = new GoogleBooksResponse(fakeItems);
        Book book = mapper.toDomain(fakeResponse);
        assertEquals(LocalDate.of(2025,1,1), book.publishedDate());
    }

    @Test
    public void ParsePublishedDateWithInvalidDateShouldThrowSieveFailure(){
        String rawDate = "g025";
        List<Volume> fakeItems = new ArrayList<>();
        fakeItems.add(new Volume("test1", new VolumeInfo("",List.of(),"",rawDate,
                "",List.of(),2,9.4, 30)));
        GoogleBooksResponse fakeResponse = new GoogleBooksResponse(fakeItems);

        SieveFailureException exception =
                assertThrows(SieveFailureException.class, () -> mapper.toDomain(fakeResponse));
        assertEquals("Encountered corrupt or completely unparsable date format: " + rawDate, exception.getMessage());
    }

    @Test
    public void ParsePublishedDateRawDateNull(){
        String rawDate = null;
        List<Volume> fakeItems = new ArrayList<>();
        fakeItems.add(new Volume("test1", new VolumeInfo("",List.of(),"",rawDate,
                "",List.of(),2,9.4, 30)));
        GoogleBooksResponse fakeResponse = new GoogleBooksResponse(fakeItems);
        assertNull(mapper.toDomain(fakeResponse).publishedDate());
    }

    @Test
    public void ParsePublishedDateRawDateEmpty(){
        String rawDate = "";
        List<Volume> fakeItems = new ArrayList<>();
        fakeItems.add(new Volume("test1", new VolumeInfo("",List.of(),"",rawDate,
                "",List.of(),2,9.4, 30)));
        GoogleBooksResponse fakeResponse = new GoogleBooksResponse(fakeItems);
       assertNull(mapper.toDomain(fakeResponse).publishedDate());
    }

    @Test
    public void extractIsbn13ShouldReturnIsbn13(){
        String isbn = "978-0-061-96436-7";
        List<Volume> fakeItems = new ArrayList<>();
        List<IndustryIdentifier> isbnList = new ArrayList<>();
        isbnList.add(new IndustryIdentifier("ISBN_13", isbn));
        fakeItems.add(new Volume("test1", new VolumeInfo("",List.of(),"","",
                "",isbnList,2,9.4, 30)));

        GoogleBooksResponse fakeResponse = new GoogleBooksResponse(fakeItems);
        Book book = mapper.toDomain(fakeResponse);
        assertEquals(isbn, book.isbn());
    }

    @Test
    public void extractIsbn13ShouldReturnIsbn10(){
        String isbn = "0-19-853453-1";
        List<Volume> fakeItems = new ArrayList<>();
        List<IndustryIdentifier> isbnList = new ArrayList<>();
        isbnList.add(new IndustryIdentifier("ISBN_10", isbn));
        fakeItems.add(new Volume("test1", new VolumeInfo("",List.of(),"","",
                "",isbnList,2,9.4, 30)));

        GoogleBooksResponse fakeResponse = new GoogleBooksResponse(fakeItems);
        Book book = mapper.toDomain(fakeResponse);
        assertEquals(isbn, book.isbn());
    }

    //todo still need to update once the isbn10 to 13 conversion is implemented
    @Test
    public void extractIsbn13ShouldReturnIsbn10ButWith(){
        String isbn = "0-19-853453-1";
        List<Volume> fakeItems = new ArrayList<>();
        List<IndustryIdentifier> isbnList = new ArrayList<>();
        isbnList.add(new IndustryIdentifier("ISBN_13", isbn));
        fakeItems.add(new Volume("test1", new VolumeInfo("",List.of(),"","",
                "",isbnList,2,9.4, 30)));

        GoogleBooksResponse fakeResponse = new GoogleBooksResponse(fakeItems);
        Book book = mapper.toDomain(fakeResponse);
        assertEquals(isbn, book.isbn());
    }
}