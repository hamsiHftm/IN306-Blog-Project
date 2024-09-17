package ch.hftm.blog.boundry;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BlogResourceTest {

    private static final String BLOG_URL = "/blogs";
    private static final String AUTH_HEADER = "Authorization";
    private static String BEARER_TOKEN;
    private static Integer blogId;

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
    @Order(1)
    public void testGetAllBlogsSuccess() {
        given()
                .when()
                .get(BLOG_URL + "?limit=10&offset=0")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }

    @Test
    @Order(2)
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
    @Order(3)
    public void testAddBlogSuccess() {
        String newBlogJson = "{ \"userId\": 1, \"title\": \"New Blog\", \"content\": \"Content of new blog\" }";

        String response = given()
                .header(AUTH_HEADER, "Bearer " + BEARER_TOKEN)
                .body(newBlogJson)
                .contentType(ContentType.JSON)
                .when()
                .post(BLOG_URL)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("isSuccess", equalTo(true))
                .extract().asString();

        blogId = io.restassured.path.json.JsonPath.from(response).getInt("data.id");
    }

    @Test
    @Order(4)
    public void testGetBlogByIdSuccess() {
        given()
                .when()
                .get(BLOG_URL + "/" + blogId)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("isSuccess", equalTo(true));
    }

    @Test
    @Order(5)
    public void testUpdateBlogSuccess() {
        String updatedBlogJson = "{ \"title\": \"Updated Title\", \"content\": \"Updated Content\" }";

        given()
                .header(AUTH_HEADER, "Bearer " + BEARER_TOKEN)
                .body(updatedBlogJson)
                .contentType(ContentType.JSON)
                .when()
                .patch(BLOG_URL + "/" + blogId)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("isSuccess", equalTo(true));
    }

    @Test
    @Order(6)
    public void testDeleteBlogSuccess() {
        given()
                .header(AUTH_HEADER, "Bearer " + BEARER_TOKEN)
                .when()
                .delete(BLOG_URL + "/" + blogId)
                .then()
                .statusCode(200)
                .body("isSuccess", equalTo(true))
                .body("data", equalTo("Blog deleted successfully"));
    }
}