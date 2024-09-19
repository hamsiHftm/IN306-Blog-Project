package ch.hftm.blog.boundry;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserResourceTest {

    private static final String USER_URL = "/user";
    private static final String AUTH_HEADER = "Authorization";
    private static String NEW_USER_TOKEN; // Token for the newly created user
    private static Long createdUserId;    // Static variable to store the created user's ID

    private static final String NEW_USER_EMAIL = "new.user@example.com";
    private static final String NEW_USER_PASSWORD = "password123";
    private static final String UPDATED_USER_PASSWORD = "newpassword123";

    // @Test
    @Order(1)
    public void testCreateUserSuccess() {
        // Create a new user
        String createUserResponse = given()
                .contentType(ContentType.JSON)
                .body("{\"email\": \"" + NEW_USER_EMAIL + "\", \"password\": \"" + NEW_USER_PASSWORD + "\", \"firstname\": \"New\", \"lastname\": \"User\", \"role\": \"user\"}")
                .when()
                .post(USER_URL)
                .then()
                .statusCode(200)
                .body("isSuccess", equalTo(true))
                .body("data.firstname", equalTo("New"))
                .body("data.lastname", equalTo("User"))
                .extract().asString();

        // Extract the ID of the created user and store it in the static variable
        createdUserId = extractUserIdFromResponse(createUserResponse);

        // Now, login with the new user's credentials to get the token
        String loginResponse = given()
                .contentType(ContentType.JSON)
                .body("{\"email\": \"" + NEW_USER_EMAIL + "\", \"password\": \"" + NEW_USER_PASSWORD + "\"}")
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .extract().asString();

        // Extract and store the token for the newly created user
        NEW_USER_TOKEN = extractTokenFromResponse(loginResponse);
    }

    private Long extractUserIdFromResponse(String response) {
        return io.restassured.path.json.JsonPath.from(response).getLong("data.id");
    }

    private String extractTokenFromResponse(String response) {
        return io.restassured.path.json.JsonPath.from(response).getString("data.token");
    }

   //  @Test
    @Order(2)
    public void testUpdateUserSuccess() {
        // Update the newly created user's details
        given()
                .header(AUTH_HEADER, "Bearer " + NEW_USER_TOKEN)
                .contentType(ContentType.JSON)
                .body("{\"firstname\": \"UpdatedFirstName\", \"lastname\": \"UpdatedLastName\"}")
                .when()
                .patch(USER_URL + "/" + createdUserId)
                .then()
                .statusCode(200)
                .body("isSuccess", equalTo(true))
                .body("data.firstname", equalTo("UpdatedFirstName"))
                .body("data.lastname", equalTo("UpdatedLastName"));
    }

    // @Test
    @Order(3)
    public void testChangePasswordSuccess() {
        // Change the password for the newly created user
        given()
                .header(AUTH_HEADER, "Bearer " + NEW_USER_TOKEN)
                .contentType(ContentType.JSON)
                .body("{\"confirmPassword\": \"" + NEW_USER_PASSWORD + "\", \"newPassword\": \"" + UPDATED_USER_PASSWORD + "\"}")
                .when()
                .patch(USER_URL + "/" + createdUserId + "/password")
                .then()
                .statusCode(200)
                .body("isSuccess", equalTo(true));

        // Update the token to use the new password after the password change
        String loginResponse = given()
                .contentType(ContentType.JSON)
                .body("{\"email\": \"" + NEW_USER_EMAIL + "\", \"password\": \"" + UPDATED_USER_PASSWORD + "\"}")
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .extract().asString();

        // Store the updated token after changing the password
        NEW_USER_TOKEN = extractTokenFromResponse(loginResponse);
    }

   // @Test
    @Order(4)
    public void testDeleteUserSuccess() {
        // Delete the newly created user
        given()
                .header(AUTH_HEADER, "Bearer " + NEW_USER_TOKEN)
                .when()
                .delete(USER_URL + "/" + createdUserId)
                .then()
                .statusCode(200)
                .body("isSuccess", equalTo(true));
    }
}