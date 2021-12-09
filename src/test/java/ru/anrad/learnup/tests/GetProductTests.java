package ru.anrad.learnup.tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.anrad.learnup.dto.Product;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.anrad.learnup.enams.ProductList.BANANA;

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
    void getProductPositiveTest() {
        Product response = given()
                .when()
                .log()
                .method()
                .log()
                .uri()
                .log()
                .headers()
                .log()
                .body()
                .expect()
                .statusCode(200)
                .when()
                .get(PRODUCT_ENDPOINT, BANANA.getId())
                .prettyPeek()
                .body()
                .as(Product.class);
        assertThat(response.getId(), equalTo(BANANA.getId()));
        assertThat(response.getTitle(), equalTo(BANANA.getTitle()));
        assertThat(response.getPrice(), equalTo(BANANA.getPrice()));
        assertThat(response.getCategoryTitle(), equalTo(BANANA.getCategory()));
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
