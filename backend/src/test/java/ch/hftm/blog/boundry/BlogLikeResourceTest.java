package ch.hftm.blog.boundry;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
public class BlogLikeResourceTest {

    private static final String BLOG_LIKE_URL = "/blog/like";
    private static final String AUTH_HEADER = "Authorization";
    private static String BEARER_TOKEN;

    @BeforeEach
    public void setup() {
        // Login as a normal user and retrieve the token
        String loginResponse = given()
                .contentType(ContentType.JSON)
                .body("{\"email\": \"jan.doe@example.com\", \"password\": \"password1\"}")
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .extract().asString();

        BEARER_TOKEN = extractTokenFromResponse(loginResponse);
    }

    private String extractTokenFromResponse(String response) {
        return io.restassured.path.json.JsonPath.from(response).getString("data.token");
    }

    @Test
    public void testAddLikeToBlogUnauthorized() {
        String invalidToken = "invalid.token.here";

        given()
                .header(AUTH_HEADER, "Bearer " + invalidToken)
                .when()
                .post(BLOG_LIKE_URL + "/" + 1 + "/" + 1)
                .then()
                .statusCode(401); // Unauthorized
    }

    @Test
    public void testRemoveLikeFromBlogUnauthorized() {
        String invalidToken = "invalid.token.here";

        given()
                .header(AUTH_HEADER, "Bearer " + invalidToken)
                .when()
                .delete(BLOG_LIKE_URL + "/" + 1 + "/" + 1)
                .then()
                .statusCode(401); // Unauthorized
    }

    @Test
    public void testRemoveLikeFromBlogBlogNotFound() {
        // Test removing like from non-existing blog
        given()
                .header(AUTH_HEADER, "Bearer " + BEARER_TOKEN)
                .when()
                .delete(BLOG_LIKE_URL + "/" + 1 + "/99999")
                .then()
                .statusCode(404)
                .contentType(ContentType.JSON)
                .body("isSuccess", equalTo(false))
                .body("data.errorMsg", equalTo("Blog not found"));
    }

    @Test
    public void testRemoveLikeFromBlogLikeNotFound() {
        // Test removing a like that doesn't exist
        given()
                .header(AUTH_HEADER, "Bearer " + BEARER_TOKEN)
                .when()
                .delete(BLOG_LIKE_URL + "/" + 1 + "/" + 1)
                .then()
                .statusCode(404)
                .contentType(ContentType.JSON)
                .body("isSuccess", equalTo(false))
                .body("data.errorMsg", equalTo("Like not found"));
    }
}