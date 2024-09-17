package ch.hftm.blog.boundry;

import ch.hftm.blog.dto.comment.CommentCreateRequestDTO1;
import ch.hftm.blog.dto.comment.CommentUpdateRequestDTO1;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CommentResourceTest {

    private String bearerToken;
    private static Long commentId;

    @BeforeEach
    public void setup() {
        // Assuming you have a method to authenticate and retrieve a bearer token
        String loginResponse = given()
                .contentType(ContentType.JSON)
                .body("{\"email\": \"jan.doe@example.com\", \"password\": \"password1\"}")
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .extract().asString();

        bearerToken = extractTokenFromResponse(loginResponse);
    }

    private String extractTokenFromResponse(String response) {
        return io.restassured.path.json.JsonPath.from(response).getString("data.token");
    }

    @Test
    @Order(1)
    public void testCreateComment() {
        CommentCreateRequestDTO1 commentDTO = new CommentCreateRequestDTO1("This is a test comment", 1L, 1L);

        Response response = given()
                .header("Authorization", "Bearer " + bearerToken)
                .contentType("application/json")
                .body(commentDTO)
                .when()
                .post("/comment")
                .then()
                .statusCode(200)
                .body("isSuccess", equalTo(true))
                .body("data", notNullValue())
                .extract().response();

        // Extract and print comment ID
        commentId = response.jsonPath().getLong("data.id");
        System.out.println("Created comment with ID: " + commentId);
        Assertions.assertNotNull(commentId, "Comment ID should not be null");
    }

    @Test
    @Order(2)
    public void testUpdateComment() {
        // Ensure commentId is set
        Assertions.assertNotNull(commentId, "Comment ID should not be null");

        CommentUpdateRequestDTO1 updateDTO = new CommentUpdateRequestDTO1("Updated comment content");

        given()
                .header("Authorization", "Bearer " + bearerToken)
                .contentType("application/json")
                .body(updateDTO)
                .when()
                .put("/comment/" + commentId)
                .then()
                .statusCode(200)
                .body("isSuccess", equalTo(true))
                .body("data.content", equalTo("Updated comment content"));
    }

    @Test
    @Order(3)
    public void testDeleteComment() {
        // Ensure commentId is set
        Assertions.assertNotNull(commentId, "Comment ID should not be null");

        // Delete the comment
        given()
                .header("Authorization", "Bearer " + bearerToken)
                .when()
                .delete("/comment/" + commentId)
                .then()
                .statusCode(200)
                .body("isSuccess", equalTo(true));
    }
}