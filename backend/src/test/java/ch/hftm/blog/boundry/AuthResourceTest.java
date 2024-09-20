package ch.hftm.blog.boundry;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
public class AuthResourceTest {

    private static final String LOGIN_URL = "/auth/login";

    @BeforeEach
    public void setup() {
        // Any setup needed before each test
    }

    @Test
    public void testLoginAdminSuccess() {
        String loginJson = "{ \"email\": \"james.wilson@example.com\", \"password\": \"password12\" }";

        given()
                .body(loginJson)
                .contentType(ContentType.JSON)
                .when()
                .post(LOGIN_URL)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("isSuccess", equalTo(true))
                .body("data.id", equalTo(12));
    }

    @Test
    public void testLoginUserSuccess() {
        String loginJson = "{ \"email\": \"jan.doe@example.com\", \"password\": \"password1\" }";

        given()
                .body(loginJson)
                .contentType(ContentType.JSON)
                .when()
                .post(LOGIN_URL)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("isSuccess", equalTo(true))
                .body("data.id", equalTo(1));
    }

    @Test
    public void testLoginUserNotFound() {
        String loginJson = "{ \"email\": \"nonexistent@example.com\", \"password\": \"password123\" }";

        given()
                .body(loginJson)
                .contentType(ContentType.JSON)
                .when()
                .post(LOGIN_URL)
                .then()
                .statusCode(404)
                .contentType(ContentType.JSON)
                .body("isSuccess", equalTo(false))
                .body("data.errorMsg", equalTo("User not found with email"));
    }

    @Test
    public void testLoginWrongPassword() {
        String loginJson = "{ \"email\": \"jan.doe@example.com\", \"password\": \"wrongpassword\" }";

        given()
                .body(loginJson)
                .contentType(ContentType.JSON)
                .when()
                .post(LOGIN_URL)
                .then()
                .statusCode(403)
                .contentType(ContentType.JSON)
                .body("isSuccess", equalTo(false))
                .body("data.errorMsg", equalTo("Wrong password"));
    }

    @Test
    public void testLoginInvalidRequest() {
        String loginJson = "{ \"email\": \"user@example.com\" }"; // Missing password

        given()
                .body(loginJson)
                .contentType(ContentType.JSON)
                .when()
                .post(LOGIN_URL)
                .then()
                .statusCode(404)
                .contentType(ContentType.JSON);
    }
}