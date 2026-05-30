package com.dc.bookaggregator.domain.services;

import com.dc.bookaggregator.domain.models.Book;

public interface BookAggregatorService {

    Book aggregateBook(String isbn);
}