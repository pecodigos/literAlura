package com.pecodigos.literalura.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pecodigos.literalura.dtos.BookDTO;
import com.pecodigos.literalura.exceptions.DataNotFoundException;
import com.pecodigos.literalura.exceptions.HttpRequestException;
import com.pecodigos.literalura.exceptions.JsonParsingException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class BookFetcherService {

    private static final String GUTENDEX_URL = "https://gutendex.com/books/?search=";

    private final ObjectMapper objectMapper;
    private final HttpClient httpClient;

    public List<BookDTO> getBooks(String bookTitle) {
        String url = GUTENDEX_URL + bookTitle.replace(" ", "+");
        System.out.println("Searching for: " + bookTitle + "\n");

        HttpResponse<String> response = sendRequest(url);

        try {
            JsonNode rootNode = objectMapper.readTree(response.body());
            JsonNode resultsNode = rootNode.get("results");

            if (resultsNode == null || !resultsNode.isArray() || resultsNode.isEmpty()) {
                throw new DataNotFoundException("No books found for book title: " + bookTitle);
            }

            return StreamSupport.stream(resultsNode.spliterator(), false)
                    .map(jsonNode -> objectMapper.convertValue(jsonNode, BookDTO.class))
                    .toList();
        } catch (IOException e) {
            throw new JsonParsingException("Error while parsing JSON response: " + e.getMessage());
        }
    }

    private HttpResponse<String> sendRequest(String url) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new HttpRequestException("Failed to get books. HTTP Status: " + response.statusCode());
            }

            return response;

        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new HttpRequestException("HTTP request failed.\n" + e.getMessage());
        }
    }
}


