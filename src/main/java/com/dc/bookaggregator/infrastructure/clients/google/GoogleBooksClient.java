package com.dc.bookaggregator.infrastructure.clients.google;

import com.dc.bookaggregator.infrastructure.configs.JacksonConfig;
import com.dc.bookaggregator.infrastructure.dtos.google.GoogleBooksResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

public class GoogleBooksClient {

    private static final String VOLUMES_URL = "https://www.googleapis.com";
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = JacksonConfig.getMapper();

    public CompletableFuture<GoogleBooksResponse> callGoogleBooksApi(String query){
       String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(VOLUMES_URL + "/books/v1/volumes?q=" + encodedQuery)).GET()
                .build();

    return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(this::readValueJackson);
    }

    public GoogleBooksResponse readValueJackson(String content) {
        try {
           return mapper.readValue(content, GoogleBooksResponse.class);
        } catch (IOException ex){
            throw new RuntimeException("Failed to parse Google Books response", ex);
        }
    }
}