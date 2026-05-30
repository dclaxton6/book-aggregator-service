package com.dc.bookaggregator.infrastructure.clients.google;

import com.dc.bookaggregator.infrastructure.dtos.google.GoogleBooksResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GoogleBooksClientTest {
    GoogleBooksClient client;
    @BeforeEach
    public void setup(){
        client = new GoogleBooksClient();
    }
    //happy path
    @Test
    public void readJacksonValueShouldReturnSuccess(){
        String mockJson = "{ \"items\": [ { \"id\": \"test1\", \"volumeInfo\": { \"title\": \"Effective Java\" } } ] }";
        GoogleBooksResponse response = client.readValueJackson(mockJson);
        assertNotNull(response);
        assertEquals("test1", response.items().getFirst().id());
        assertEquals("Effective Java", response.items().getFirst().volumeInfo().title());
    }

    //broken path
    @Test
    public void readJacksonValueShouldReturnFailure(){
        String corruptJson = "{ corrupt-garbage-text ]";
        RuntimeException exception =
        assertThrows(RuntimeException.class, () -> client.readValueJackson(corruptJson));
        assertEquals("Failed to parse Google Books response", exception.getMessage());
    }
}