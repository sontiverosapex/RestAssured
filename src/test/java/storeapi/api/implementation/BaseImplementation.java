package storeapi.api.implementation;

import io.restassured.RestAssured;

public class BaseImplementation {
    public BaseImplementation() {
        RestAssured.baseURI = "https://api.escuelajs.co/api/v1";
    }
}
