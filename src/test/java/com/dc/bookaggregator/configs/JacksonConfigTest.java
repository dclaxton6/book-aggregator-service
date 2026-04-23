package com.dc.bookaggregator.configs;

import com.dc.bookaggregator.infrastructure.configs.JacksonConfig;
import com.dc.bookaggregator.infrastructure.dtos.google.GoogleBooksResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JacksonConfigTest {
    @Test
    public void ShouldMapJsonToRecordWithCorrectSpelling() throws JsonProcessingException {
        String dummyJson = """
      {
      "items": [
        {
          "id": "test_id_123",
          "volumeInfo": {
            "title": "Clean Code",
            "authors": ["Robert C. Martin"],
            "industryIdentifiers": [
              { "type": "ISBN_13", "identifier": "9780132350884" }
            ],
            "pageCount": 464
          }
        }
      ]
    }
    """;

         GoogleBooksResponse googleBook = JacksonConfig.getMapper().readValue(dummyJson, GoogleBooksResponse.class);
         assertFalse(googleBook.items().isEmpty());
         assertEquals("test_id_123", googleBook.items().getFirst().id());
         assertEquals("Clean Code", googleBook.items().getFirst().volumeInfo().title());
         assertEquals("Robert C. Martin", googleBook.items().getFirst().volumeInfo().authors().getFirst());
         assertEquals("ISBN_13", googleBook.items().getFirst().volumeInfo().industryIdentifiers().getFirst().type());
         assertEquals("9780132350884", googleBook.items().getFirst().volumeInfo().industryIdentifiers().getFirst().identifier());
         assertEquals(464, googleBook.items().getFirst().volumeInfo().pageCount());
    }

    @Test
    public void ShouldThrowExceptionWhenMappingJsonToRecordWithIncorrectSpelling(){
        String dummyJson = """
      {
      "items": [
        {
          "id": "test_id_123",
          "volumeInfo": {
            "title": "Clean Code",
            "authors": ["Robert C. Martin"],
            "industryIdentiiers": [
              { "type": "ISBN_13", "identifier": "9780132350884" }
            ],
            "pageCount": 464
          }
        }
      ]
    }
    """;
        assertThrows(UnrecognizedPropertyException.class,
                () -> JacksonConfig.getMapper().readValue(dummyJson, DummyRecord.class));
    }

    private record DummyRecord(String kind){}
}