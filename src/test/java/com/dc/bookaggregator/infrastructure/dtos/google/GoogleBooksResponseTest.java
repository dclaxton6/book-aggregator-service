package com.dc.bookaggregator.infrastructure.dtos.google;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class GoogleBooksResponseTest {

    @Test
    public void ShouldReturnEmptyListWhenItemsAreNull(){
        GoogleBooksResponse response = new GoogleBooksResponse(null);
        assertAll(
                () -> assertNotNull(response.items()),
                () -> assertTrue(response.items().isEmpty())
        );
    }

    @Test
    public void ShouldThrowUnsupportedOperationExceptionWhenAddingNewVolumeToList(){
        ArrayList<Volume> mutableItems = new ArrayList<>();
        var item1 = new Volume("1", new VolumeInfo("test1",null,null,null,null,null,0,0.0,0));
        var item2 = new Volume("2", new VolumeInfo("test2",null,null,null,null,null,0,0.0,0));
        mutableItems.add(item1);
        GoogleBooksResponse response = new GoogleBooksResponse(mutableItems);

        assertThrows(UnsupportedOperationException.class, () -> response.items().add(item2));
    }
}