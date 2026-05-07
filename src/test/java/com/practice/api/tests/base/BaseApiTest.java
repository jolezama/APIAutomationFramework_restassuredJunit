package com.practice.api.tests.base;

import com.practice.api.services.BookStoreApi;
import org.junit.jupiter.api.BeforeEach;

public abstract class BaseApiTest {

    protected BookStoreApi bookStoreApi;

    @BeforeEach
    protected void setUp() {
        bookStoreApi = new BookStoreApi();
    }
}