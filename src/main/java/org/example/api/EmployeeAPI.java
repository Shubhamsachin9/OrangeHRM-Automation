package org.example.api;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class EmployeeAPI {
    private static final String API_KEY = "pro_19026a5078864a80e044a0160ffa56de959bcaa8879a811a89da43399d499ce4";

    public Response getUser(int userId) {

        return given()
                .header("x-api-key", API_KEY)
                .header("X-Reqres-Env", "prod")
                .when()
                .get("https://reqres.in/api/users/" + userId);
    }
}
