package ru.anrad.learnup.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GetProductTests {
    public static final String PRODUCT_ENDPOINT="products/{id}";
    static Properties properties = new Properties();

    @BeforeAll
    static void setUp() throws IOException {
        properties.load(new FileInputStream("src/test/resources/application.properties"));
        RestAssured.baseURI = properties.getProperty("baseURL");;
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
    void getProductPositiveTest() {
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
                .get(PRODUCT_ENDPOINT, 17563)
                .prettyPeek()
                .then()
                .statusCode(200)
                .body("id", equalTo(17563))
                .body("title", equalTo("Bread"))
                .body("price", equalTo(100))
                .body("categoryTitle", equalTo("Food"));
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
    void getProductNegativeIdIsFractWithPointTest() {
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
    void getProductNegativeIdIsFractWithCommaTest() {
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
}
