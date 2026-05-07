package com.practice.api.services;

import com.practice.api.client.ApiClient;
import com.practice.api.endpoints.BookStoreEndpoints;
import io.restassured.response.Response;

public class BookStoreApi {

    public Response getAllBooks() {
        return ApiClient.request()
                .when()
                .get(BookStoreEndpoints.GET_BOOKS);
    }

    public Response getBookByIsbn(String isbn) {
        return ApiClient.request()
                .queryParam("ISBN", isbn)
                .when()
                .get(BookStoreEndpoints.GET_BOOK_BY_ISBN);
    }
}