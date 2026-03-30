package com.petstore.automation.api;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

/**
 * Cliente HTTP Pet Store: URI base explícita por llamada y filtro Allure (adjuntos request/response).
 */
public final class ApiMethods {

    private ApiMethods() {
    }

    public static Response getPetById(String baseUrl, String id) {
        return given()
                .filter(new AllureRestAssured())
                .baseUri(baseUrl)
                .get("/" + id);
    }

    public static Response createPet(String baseUrl, Object body) {
        return given()
                .filter(new AllureRestAssured())
                .baseUri(baseUrl)
                .contentType(ContentType.JSON)
                .body(body)
                .post();
    }

    /**
     * Actualización completa según contrato Pet Store: PUT {@code /pet} con cuerpo completo.
     */
    public static Response updatePet(String baseUrl, Object body) {
        return given()
                .filter(new AllureRestAssured())
                .baseUri(baseUrl)
                .contentType(ContentType.JSON)
                .body(body)
                .put();
    }
}
