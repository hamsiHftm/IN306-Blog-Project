package ch.hftm.blog.boundry;

import ch.hftm.blog.dto.rating.RatingResponseDTO1;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RatingResourceTest {

    private String bearerToken;
    private static Long blogId = 1L;

    @BeforeEach
    public void setup() {
        // Simulate a login to retrieve a bearer token
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
    public void testAddRating() {
        int rating = 4;

        given()
                .header("Authorization", "Bearer " + bearerToken)
                .contentType(ContentType.JSON)
                .when()
                .post("/rating/" + blogId + "/" + rating)
                .then()
                .statusCode(200)
                .body("isSuccess", equalTo(true));
    }

    @Test
    @Order(2)
    public void testUpdateRating() {
        int updatedRating = 5;

        given()
                .header("Authorization", "Bearer " + bearerToken)
                .contentType(ContentType.JSON)
                .when()
                .put("/rating/" + blogId + "/" + updatedRating)
                .then()
                .statusCode(200)
                .body("isSuccess", equalTo(true));
    }

    @Test
    @Order(3)
    public void testRemoveRating() {

        given()
                .header("Authorization", "Bearer " + bearerToken)
                .contentType(ContentType.JSON)
                .when()
                .delete("/rating/" + blogId)
                .then()
                .statusCode(200)
                .body("isSuccess", equalTo(true));

    }

    @Test
    @Order(4)
    public void testGetAverageRating() {

        Response response = given()
                .header("Authorization", "Bearer " + bearerToken)
                .contentType(ContentType.JSON)
                .when()
                .get("/rating/" + blogId)
                .then()
                .statusCode(200)
                .body("isSuccess", equalTo(true))
                .body("data", notNullValue())
                .extract().response();

        // Extracting and validating the average rating
        RatingResponseDTO1 ratingResponse = response.jsonPath().getObject("data", RatingResponseDTO1.class);
        Assertions.assertNotNull(ratingResponse);
        Assertions.assertTrue(ratingResponse.averageRating() >= 0);
    }
}