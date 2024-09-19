package ch.hftm.blog.boundry;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
public class CommentLikeResourceTest {

    private static final String COMMENT_LIKE_URL = "/comment/like";
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

//    @Test
//    public void testAddLikeToCommentSuccess() {
//        // Add a like to a comment
//        given()
//                .header(AUTH_HEADER, "Bearer " + BEARER_TOKEN)
//                .when()
//                .post(COMMENT_LIKE_URL + "/" + 1 + "/" + 1)
//                .then()
//                .statusCode(200)
//                .body("isSuccess", equalTo(true));
//    }

//    @Test
//    public void testRemoveLikeFromCommentSuccess() {
//        // Remove a like from a comment
//        given()
//                .header(AUTH_HEADER, "Bearer " + BEARER_TOKEN)
//                .when()
//                .delete(COMMENT_LIKE_URL + "/" + 1 + "/" + 1)
//                .then()
//                .statusCode(200)
//                .body("isSuccess", equalTo(true));
//    }

    @Test
    public void testAddLikeToCommentUnauthorized() {
        String invalidToken = "invalid.token.here";

        // Attempt to like a comment with an invalid token
        given()
                .header(AUTH_HEADER, "Bearer " + invalidToken)
                .when()
                .post(COMMENT_LIKE_URL + "/" + 1 + "/" + 1)
                .then()
                .statusCode(401); // Unauthorized
    }

    @Test
    public void testRemoveLikeFromCommentUnauthorized() {
        String invalidToken = "invalid.token.here";

        // Attempt to remove a like with an invalid token
        given()
                .header(AUTH_HEADER, "Bearer " + invalidToken)
                .when()
                .delete(COMMENT_LIKE_URL + "/" + 1 + "/" + 1)
                .then()
                .statusCode(401); // Unauthorized
    }

    @Test
    public void testRemoveLikeFromCommentCommentNotFound() {
        // Attempt to remove a like from a non-existing comment
        given()
                .header(AUTH_HEADER, "Bearer " + BEARER_TOKEN)
                .when()
                .delete(COMMENT_LIKE_URL + "/" + 1 + "/99999") // Non-existing comment ID
                .then()
                .statusCode(404)
                .contentType(ContentType.JSON)
                .body("isSuccess", equalTo(false))
                .body("data.errorMsg", equalTo("Comment not found"));
    }

//    @Test
//    public void testRemoveLikeFromCommentLikeNotFound() {
//        // Attempt to remove a like that doesn't exist for a comment
//        given()
//                .header(AUTH_HEADER, "Bearer " + BEARER_TOKEN)
//                .when()
//                .delete(COMMENT_LIKE_URL + "/" + 1 + "/" + 1)
//                .then()
//                .statusCode(404)
//                .contentType(ContentType.JSON)
//                .body("isSuccess", equalTo(false))
//                .body("data.errorMsg", equalTo("Like not found"));
//    }
}