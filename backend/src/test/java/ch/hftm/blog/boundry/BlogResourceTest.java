package ch.hftm.blog.boundry;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
public class BlogResourceTest {

    private static final String BLOG_URL = "/blogs";
    private static final String AUTH_HEADER = "Authorization";
    private static String BEARER_TOKEN;

    @BeforeEach
    public void setup() {
        // Login as a normal user and retrieve the token
        String loginResponse = given()
                .contentType(ContentType.JSON)
                .body("{\"email\": \"jan.doe@example.com\", \"password\": \"password1\"}")
                .when()
                .post("/auth/login") // Adjust the endpoint based on your authentication service
                .then()
                .statusCode(200)
                .extract().asString();

        // Extract the token from the response
        BEARER_TOKEN = extractTokenFromResponse(loginResponse);
    }

    private String extractTokenFromResponse(String response) {
        // Assuming the response is in JSON format and contains a field "token"
        return io.restassured.path.json.JsonPath.from(response).getString("token");
    }

    @Test
    public void testGetAllBlogsSuccess() {
        given()
                .when()
                .get(BLOG_URL + "?limit=10&offset=0")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }

    @Test
    public void testGetBlogByIdSuccess() {
        given()
                .when()
                .get(BLOG_URL + "/1")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("isSuccess", equalTo(true));
    }

    @Test
    public void testGetFavoriteBlogsByUserIdSuccess() {
        given()
                .header(AUTH_HEADER, "Bearer " + BEARER_TOKEN)
                .when()
                .get(BLOG_URL + "/favourites/1?limit=10&offset=0")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("isSuccess", equalTo(true));
    }

    @Test
    public void testAddBlogSuccess() {
        String newBlogJson = "{ \"userId\": 1, \"title\": \"New Blog\", \"content\": \"Content of new blog\" }";

        given()
                .header(AUTH_HEADER, "Bearer " + BEARER_TOKEN)
                .body(newBlogJson)
                .contentType(ContentType.JSON)
                .when()
                .post(BLOG_URL)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("isSuccess", equalTo(true));
    }

    @Test
    public void testUpdateBlogSuccess() {
        String updatedBlogJson = "{ \"title\": \"Updated Title\", \"content\": \"Updated Content\" }";

        given()
                .header(AUTH_HEADER, "Bearer " + BEARER_TOKEN)
                .body(updatedBlogJson)
                .contentType(ContentType.JSON)
                .when()
                .patch(BLOG_URL + "/1")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("isSuccess", equalTo(true));
    }

    @Test
    public void testDeleteBlogSuccess() {
        given()
                .header(AUTH_HEADER, "Bearer " + BEARER_TOKEN)
                .when()
                .delete(BLOG_URL + "/1")
                .then()
                .statusCode(200)
                .body(equalTo("Blog deleted successfully")); // Adjust if the response is different
    }

}