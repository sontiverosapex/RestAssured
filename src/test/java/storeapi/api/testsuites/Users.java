package storeapi.api.testsuites;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import storeapi.api.implementation.UserImplementation;
import storeapi.models.User;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class Users {

    UserImplementation userImplementation = new UserImplementation();
    Response response;

    @Test
    public void getAllUsers() {
        response = userImplementation.getAllUsers();
        assertThat(response.statusCode(), equalTo(200));
    }

    @Test
    public void getSingleUser(){
        response = userImplementation.getSingleUser();
        assertThat(response.statusCode(), equalTo(200));
    }

    @Test
    public void createUser(){
        User user = userImplementation.user();
        response = userImplementation.createUser(user);
        User responseUser = response.as(User.class);
        assertThat(response.statusCode(), equalTo(201));
        assertThat(responseUser.getName(), equalTo(user.getName()));
        assertThat(responseUser.getEmail(), equalTo(user.getEmail()));
    }

    @Test
    public void updateUser(){
        response = userImplementation.updateUser();
        assertThat(response.statusCode(), equalTo(200));
    }
}
