package storeapi.api.implementation;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.datafaker.Faker;
import storeapi.models.User;
import java.util.ArrayList;
import java.util.Random;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class UserImplementation extends BaseImplementation {

    public UserImplementation() {
        RestAssured.basePath = "/users";
    }

    public Response getAllUsers() {
        return given().
                    relaxedHTTPSValidation().
                    contentType(ContentType.JSON).
                when().
                    get().
                then().
                    assertThat().
                    extract().
                    response();
    }

    public Response getSingleUser(){
        return
                given().
                    relaxedHTTPSValidation().
                    contentType(ContentType.JSON).
                    pathParam("userId",getRandomUser()).
                when().
                    get("/{userId}").
                then().
                    body(matchesJsonSchemaInClasspath("schemas/User.json")).
                    extract().
                    response();
    }

    public Response createUser(User user){
        return
                given().
                    relaxedHTTPSValidation().
                    contentType(ContentType.JSON).
                    body(user).
                when().
                    post().
                then().
                    body(matchesJsonSchemaInClasspath("schemas/User.json")).
                    extract().
                    response();
    }

    public Response updateUser(){
        return
                given().
                    relaxedHTTPSValidation().
                    contentType(ContentType.JSON).
                    pathParam("userId", getRandomUser()).
                    body(user()).
                when().
                    put("/{userId}").
                then().
                    body(matchesJsonSchemaInClasspath("schemas/User.json")).
                    extract().
                    response();
    }

    public int getRandomUser(){
        Random random = new Random();
        ArrayList<Integer> userId =
                given().
                    relaxedHTTPSValidation().
                    contentType(ContentType.JSON).
                when().
                    get().
                then().
                    extract().
                    path("id");

        return userId.get(random.nextInt(userId.size()));
    }

    public User user(){
        Faker faker = new Faker();
        return User.builder().
                name(faker.name().fullName()).
                email(faker.internet().emailAddress()).
                password(faker.number().digits(8)).
                avatar(faker.internet().url()).
                role("customer").
                build();
    }
}
