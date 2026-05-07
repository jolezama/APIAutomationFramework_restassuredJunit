package com.practice.api.client;

import com.practice.api.config.ConfigManager;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public final class ApiClient {

    private ApiClient() {
    }

    public static RequestSpecification request() {
        RequestSpecification specification = new RequestSpecBuilder()
                .setBaseUri(ConfigManager.getBaseUrl())
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .setRelaxedHTTPSValidation()
                .build();

        return given()
                .spec(specification)
                .log().ifValidationFails();
    }
}