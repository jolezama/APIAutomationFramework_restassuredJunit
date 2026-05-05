package com.practice.api;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

public class BookStoreGetBooksTest {

    private static final String BASE_URL = "https://bookstore.toolsqa.com";

    @Test
    void shouldGetAllBooksSuccessfully() {
        given()
            .baseUri(BASE_URL)
        .when()
            .get("/BookStore/v1/Books")
        .then()
            .statusCode(200)
            .body("books", not(empty()))
            .body("books[0].isbn", notNullValue())
            .body("books[0].title", notNullValue())
            .body("books[0].author", notNullValue());
    }
}