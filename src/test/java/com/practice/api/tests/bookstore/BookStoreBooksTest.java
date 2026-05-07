package com.practice.api.tests.bookstore;

import com.practice.api.models.Book;
import com.practice.api.models.BooksResponse;
import com.practice.api.tests.base.BaseApiTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@Tag("bookstore")
public class BookStoreBooksTest extends BaseApiTest {

    @Test
    @DisplayName("GET /BookStore/v1/Books should return a non-empty books collection")
    void shouldReturnAllBooksSuccessfully() {
        Response response = bookStoreApi.getAllBooks();

        response.then()
                .statusCode(200)
                .body("books", not(empty()))
                .body("books[0].isbn", notNullValue())
                .body("books[0].title", notNullValue())
                .body("books[0].author", notNullValue());

        BooksResponse booksResponse = response.as(BooksResponse.class);

        assertAll(
                () -> assertNotNull(booksResponse, "Books response should not be null"),
                () -> assertNotNull(booksResponse.getBooks(), "Books list should not be null"),
                () -> assertFalse(booksResponse.getBooks().isEmpty(), "Books list should not be empty")
        );
    }

    @Test
    @DisplayName("GET /BookStore/v1/Books should contain Git Pocket Guide")
    void shouldContainGitPocketGuideBook() {
        Response response = bookStoreApi.getAllBooks();

        response.then()
                .statusCode(200)
                .body("books.title", hasItem("Git Pocket Guide"))
                .body("books.find { it.title == 'Git Pocket Guide' }.author", equalTo("Richard E. Silverman"))
                .body("books.find { it.title == 'Git Pocket Guide' }.publisher", equalTo("O'Reilly Media"));
    }

    @Test
    @DisplayName("GET /BookStore/v1/Book should return a book when ISBN is valid")
    void shouldReturnBookByValidIsbn() {
        String isbn = "9781449325862";

        Response response = bookStoreApi.getBookByIsbn(isbn);

        response.then()
                .statusCode(200)
                .body("isbn", equalTo(isbn))
                .body("title", equalTo("Git Pocket Guide"))
                .body("author", equalTo("Richard E. Silverman"));

        Book book = response.as(Book.class);

        assertAll(
                () -> assertEquals(isbn, book.getIsbn(), "ISBN should match"),
                () -> assertEquals("Git Pocket Guide", book.getTitle(), "Book title should match"),
                () -> assertNotNull(book.getPublisher(), "Publisher should not be null")
        );
    }

    @Test
    @DisplayName("GET /BookStore/v1/Books should return books with required fields")
    void shouldReturnBooksWithRequiredFields() {
        Response response = bookStoreApi.getAllBooks();

        BooksResponse booksResponse = response.then()
                .statusCode(200)
                .extract()
                .as(BooksResponse.class);

        List<Book> books = booksResponse.getBooks();

        assertAll(
                () -> assertFalse(books.isEmpty(), "Books list should not be empty"),
                () -> assertTrue(
                        books.stream().allMatch(book -> book.getIsbn() != null && !book.getIsbn().isBlank()),
                        "Every book should have ISBN"
                ),
                () -> assertTrue(
                        books.stream().allMatch(book -> book.getTitle() != null && !book.getTitle().isBlank()),
                        "Every book should have title"
                ),
                () -> assertTrue(
                        books.stream().allMatch(book -> book.getAuthor() != null && !book.getAuthor().isBlank()),
                        "Every book should have author"
                )
        );
    }

    @ParameterizedTest(name = "Book title {0} should have author {1}")
    @CsvSource({
            "Git Pocket Guide, Richard E. Silverman",
            "Learning JavaScript Design Patterns, Addy Osmani",
            "Designing Evolvable Web APIs with ASP.NET, Glenn Block et al."
    })
    @DisplayName("GET /BookStore/v1/Books should validate known book authors")
    void shouldValidateKnownBookAuthors(String title, String expectedAuthor) {
        Response response = bookStoreApi.getAllBooks();

        response.then()
                .statusCode(200)
                .body("books.find { it.title == '" + title + "' }.author", equalTo(expectedAuthor));
    }
}