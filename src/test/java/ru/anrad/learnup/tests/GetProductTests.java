package ru.anrad.learnup.tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class GetProductTests {
    public static final String PRODUCT_ENDPOINT = "products/{id}";
    static Properties properties = new Properties();

    @BeforeAll
    static void setUp() throws IOException {
        properties.load(new FileInputStream("src/test/resources/application.properties"));
        RestAssured.baseURI = properties.getProperty("baseURL");
    }

    @Test
    void getAllProductsPositiveTest() {
        given()
                .when()
                .log()
                .method()
                .log()
                .uri()
                .log()
                .headers()
                .log()
                .body()
                .when()
                .get("/products")
                .prettyPeek()
                .then()
                .statusCode(200);
    }

    @Test
    void getProductPositiveTestFirst() {
        given()
                .when()
                .log()
                .method()
                .log()
                .uri()
                .log()
                .headers()
                .log()
                .body()
                .when()
                .get(PRODUCT_ENDPOINT, 18676)
                .prettyPeek()
                .then()
                .statusCode(200)
                .body("id", equalTo(18676))
                .body("title", equalTo("Bread"))
                .body("price", equalTo(300))
                .body("categoryTitle", equalTo("Food"));
    }

    @Test
    void getProductPositiveTestSecond() {
        given()
                .when()
                .log()
                .method()
                .log()
                .uri()
                .log()
                .headers()
                .log()
                .body()
                .when()
                .get(PRODUCT_ENDPOINT, 18412)
                .prettyPeek()
                .then()
                .statusCode(200)
                .body("id", equalTo(18412))
                .body("title", equalTo("Cow"))
                .body("price", equalTo(100))
                .body("categoryTitle", equalTo("Food"));
    }

    @Test
    void getProductPositiveTestThird() {
        given()
                .when()
                .log()
                .method()
                .log()
                .uri()
                .log()
                .headers()
                .log()
                .body()
                .when()
                .get(PRODUCT_ENDPOINT, 18468)
                .prettyPeek()
                .then()
                .statusCode(200)
                .body("id", equalTo(18468))
                .body("title", equalTo("Fringilla Vigo"))
                .body("price", equalTo(137))
                .body("categoryTitle", equalTo("Furniture"));
    }

    @Test
    void getProductPositiveWithSpacesTest() {
        given()
                .when()
                .log()
                .method()
                .log()
                .uri()
                .log()
                .headers()
                .log()
                .body()
                .when()
                .get(PRODUCT_ENDPOINT, "    18468     ")
                .prettyPeek()
                .then()
                .statusCode(200)
                .body("id", equalTo(18468))
                .body("title", equalTo("Fringilla Vigo"))
                .body("price", equalTo(137))
                .body("categoryTitle", equalTo("Furniture"));
    }

    @Test
    void getProductNegativeIdIsNotExistedTest() {
        given()
                .when()
                .log()
                .method()
                .log()
                .uri()
                .log()
                .headers()
                .log()
                .body()
                .when()
                .get(PRODUCT_ENDPOINT, 1)
                .prettyPeek()
                .then()
                .statusCode(404)
                .body("status", equalTo(404))
                .body("message", equalTo("Unable to find product with id: 1"));
    }

    @Test
    void getProductNegativeIdIsSymbolTest() {
        given()
                .when()
                .log()
                .method()
                .log()
                .uri()
                .log()
                .headers()
                .log()
                .body()
                .when()
                .get(PRODUCT_ENDPOINT, ".")
                .prettyPeek()
                .then()
                .statusCode(400)
                .body("status", equalTo(400))
                .body("error", equalTo("Bad Request"));
    }

    @Test
    void getProductNegativeIdIsNegativeTest() {
        given()
                .when()
                .log()
                .method()
                .log()
                .uri()
                .log()
                .headers()
                .log()
                .body()
                .when()
                .get(PRODUCT_ENDPOINT, "-1")
                .prettyPeek()
                .then()
                .statusCode(400)
                .body("status", equalTo(400))
                .body("error", equalTo("Bad Request"));
    }

    @Test
    void getProductNegativeIdIsRationalWithPointTest() {
        given()
                .when()
                .log()
                .method()
                .log()
                .uri()
                .log()
                .headers()
                .log()
                .body()
                .when()
                .get(PRODUCT_ENDPOINT, 3.7)
                .prettyPeek()
                .then()
                .statusCode(400)
                .body("status", equalTo(400))
                .body("error", equalTo("Bad Request"));
    }

    @Test
    void getProductNegativeIdIsRationalWithCommaTest() {
        given()
                .when()
                .log()
                .method()
                .log()
                .uri()
                .log()
                .headers()
                .log()
                .body()
                .when()
                .get(PRODUCT_ENDPOINT, "3,7")
                .prettyPeek()
                .then()
                .statusCode(400)
                .body("status", equalTo(400))
                .body("error", equalTo("Bad Request"));
    }

    @Test
    void getProductNegativeSQLInjectionCommentTest() {
        given()
                .when()
                .log()
                .method()
                .log()
                .uri()
                .log()
                .body()
                .when()
                .get(PRODUCT_ENDPOINT, "1 -- hello ")
                .prettyPeek()
                .then()
                .statusCode(400)
                .contentType(ContentType.JSON);
    }

    @Test
    void getProductNegativeXSSTest() {
        given()
                .when()
                .log()
                .method()
                .log()
                .uri()
                .log()
                .body()
                .when()
                .get(PRODUCT_ENDPOINT, "<script>alert(Hello, World!)</script>")
                .prettyPeek()
                .then()
                .statusCode(400)
                .contentType(ContentType.JSON);
    }
}
