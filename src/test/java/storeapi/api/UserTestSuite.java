package storeapi.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import storeapi.models.User;

import java.util.ArrayList;
import java.util.Random;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class UserTestSuite {
    @BeforeTest
    public void setup() {
        RestAssured.baseURI = "https://api.escuelajs.co/api/v1";
    }

    @Test
    public void getAllUsers() {
        Response response =
                given().
                    relaxedHTTPSValidation().
                    contentType(ContentType.JSON).
                when().
                    get("/users").
                then().
                    assertThat().
                    extract().
                    response();

        assertThat(response.statusCode(), equalTo(200));
    }

    @Test
    public void getSingleUser(){
        int randomUser = 0;
        Random random = new Random();
        ArrayList<Integer> userId =
                given().
                    relaxedHTTPSValidation().
                    contentType(ContentType.JSON).
                when().
                    get("/users").
                then().
                    extract().
                    path("id");

        randomUser = userId.get(random.nextInt(userId.size()));

        Response response =
                given().
                    relaxedHTTPSValidation().
                    contentType(ContentType.JSON).
                    pathParam("id",randomUser).
                when().
                    get("/users/{id}").
                then().
                    body(matchesJsonSchemaInClasspath("schemas/User.json")).
                    extract().
                    response();

        User user = response.as(User.class);
        assertThat(response.statusCode(), equalTo(200));
        assertThat(randomUser,equalTo(user.getId()));
    }

    @Test
    public void createUser(){
        User user = User.builder().
                name("T Name").
                email("mail@test.com").
                password("1234").
                avatar("https://picsum.photos/800").
                build();

        Response response =
                given().
                    relaxedHTTPSValidation().
                    contentType(ContentType.JSON).
                    body(user).
                when().
                    post("/users").
                then().
                    body(matchesJsonSchemaInClasspath("schemas/User.json")).
                    extract().
                    response();

        User responseUser = response.as(User.class);
        assertThat(response.statusCode(), equalTo(201));
        assertThat(responseUser.getName(), equalTo(user.getName()));
        assertThat(responseUser.getEmail(), equalTo(user.getEmail()));
    }

    @Test
    public void updateUser(){
        // get random user
        int randomUser = 0;
        Random random = new Random();
        ArrayList<Integer> userId =
                given().
                    relaxedHTTPSValidation().
                    contentType(ContentType.JSON).
                when().
                    get("/users").
                then().
                    extract().
                        path("id");

        randomUser = userId.get(random.nextInt(userId.size()));

        // create a user object
        User user = User.builder().
                name("T Name").
                email("mail@test.com").
                password("1234").
                avatar("https://picsum.photos/800").
                role("customer").
                build();

        // update a user with the user just created
        Response response =
                given().
                    relaxedHTTPSValidation().
                    contentType(ContentType.JSON).
                    pathParam("userId", randomUser).
                    body(user).
                when().
                    put("/users/{userId}").
                then().
                    body(matchesJsonSchemaInClasspath("schemas/User.json")).
                    extract().
                    response();

        System.out.println(response.print());
    }
}
