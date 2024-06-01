package backend.api_methods;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class api_methods {

    /**
     * @param endPoint Service Url
     * @param parameter Parameter to be searched
     * @return Service execution response
     */
    public static Response getMethod(String endPoint, String parameter) {
        RestAssured.baseURI = endPoint;
        return RestAssured.get(parameter);
    }

    /**
     * Execute a Post method Rest service
     *
     * @param endpoint    Service Url
     * @param bodyRequest Body that is sent in the request
     * @return Service execution response
     */
    public static Response postMethod(String endpoint, Object bodyRequest) {
        RestAssured.baseURI = endpoint;
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .body(bodyRequest)
                .post();
    }

    /**
     * Execute a Put method Rest service for update information
     *
     * @param endpoint    Service Url
     * @param bodyRequest Body that is sent in the request
     * @return Service execution response
     */
    public static Response putMethod(String endpoint, String idUser, Object bodyRequest) {
        RestAssured.baseURI = endpoint;
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .body(bodyRequest)
                .put(idUser);
    }

}
