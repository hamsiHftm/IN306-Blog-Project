package ch.hftm.blog.boundry;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class BlogResourceTest {

    @BeforeEach
    public void setup() {
        // Setup before each test if needed
    }

    @Test
    public void testGetAllBlogs() {
        given()
                .when().get("/blogs/public")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }

    @Test
    public void testGetBlogById() {
        given()
                .when().get("/blogs/public/1")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }

    @Test
    public void testAddBlog() {
        String newBlogJson = "{ \"userId\": 1, \"title\": \"New Blog\", \"content\": \"Content of new blog\" }";

        given()
                .body(newBlogJson)
                .contentType(ContentType.JSON)
                .when().post("/blogs")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }

    @Test
    public void testUpdateBlog() {
        String updatedBlogJson = "{ \"title\": \"Updated Title\", \"content\": \"Updated Content\" }";

        given()
                .body(updatedBlogJson)
                .contentType(ContentType.JSON)
                .when().patch("/blogs/1")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }

    @Test
    public void testDeleteBlog() {
        given()
                .when().delete("/blogs/1")
                .then()
                .statusCode(200);
    }
}