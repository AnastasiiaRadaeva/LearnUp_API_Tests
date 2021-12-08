package ru.anrad.learnup.tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.*;
import ru.anrad.learnup.dto.Product;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class PostProductTests {
    public static final String PRODUCT_ENDPOINT="products";
    static Properties properties = new Properties();

    static Product product;
    Integer id;

    @BeforeAll
    static void setUp() throws IOException {
        properties.load(new FileInputStream("src/test/resources/application.properties"));
        RestAssured.baseURI = properties.getProperty("baseURL");
    }

    @BeforeEach
    void init_product(){
        product = Product.builder()
                .price(100)
                .title("Banana")
                .categoryTitle("Food")
                .id(null)
                .build();
    }

    @Test
    void postProductPositiveTest() {
        id = given()
                .body(product.toString())
                .header("Content-Type", "application/json")
                .log()
                .method()
                .log()
                .uri()
                .log()
                .headers()
                .log()
                .body()
                .expect()
                .statusCode(201)
                .when()
                .post(PRODUCT_ENDPOINT)
                .prettyPeek()
                .jsonPath()
                .get("id");
    }

    @Test
    @Tag("SkipCleanup")
    void postProductNegativeProductIsWrongStructureTest() {
        given()
                .body("string")
                .header("Content-Type", "application/json")
                .log()
                .method()
                .log()
                .uri()
                .log()
                .headers()
                .log()
                .body()
                .expect()
                .statusCode(400)
                .when()
                .post(PRODUCT_ENDPOINT)
                .prettyPeek();
    }

    @Test
    @Tag("SkipCleanup")
    void postProductNegativeIdIsNotNullTest() {
        product.setId(6);

        id = given()
                .body(product.toString())
                .header("Content-Type", "application/json")
                .log()
                .method()
                .log()
                .uri()
                .log()
                .headers()
                .log()
                .body()
                .expect()
                .statusCode(400)
                .when()
                .post(PRODUCT_ENDPOINT)
                .prettyPeek()
                .jsonPath()
                .get("id");
    }

    @Test
    void postProductNegativeTitleIsNullTest() {
        product.setTitle(null);

        id = given()
                .body(product.toString())
                .header("Content-Type", "application/json")
                .log()
                .method()
                .log()
                .uri()
                .log()
                .headers()
                .log()
                .body()
                .expect()
                .statusCode(400)
                .when()
                .post(PRODUCT_ENDPOINT)
                .prettyPeek()
                .jsonPath()
                .get("id");
    }

    @Test
    void postProductNegativeTitleIsEmptyTest() {
        product.setTitle("");

        id = given()
                .body(product.toString())
                .header("Content-Type", "application/json")
                .log()
                .method()
                .log()
                .uri()
                .log()
                .headers()
                .log()
                .body()
                .expect()
                .statusCode(400)
                .when()
                .post(PRODUCT_ENDPOINT)
                .prettyPeek()
                .jsonPath()
                .get("id");
    }

    @Test
    void postProductNegativePriceIsNullTest() {
        product.setPrice(null);

        id = given()
                .body(product.toString())
                .header("Content-Type", "application/json")
                .log()
                .method()
                .log()
                .uri()
                .log()
                .headers()
                .log()
                .body()
                .expect()
                .statusCode(400)
                .when()
                .post(PRODUCT_ENDPOINT)
                .prettyPeek()
                .jsonPath()
                .get("id");
    }

    @Test
    void postProductNegativePriceIsNegativeTest() {
        product.setPrice(-50);

        id = given()
                .body(product.toString())
                .header("Content-Type", "application/json")
                .log()
                .method()
                .log()
                .uri()
                .log()
                .headers()
                .log()
                .body()
                .expect()
                .statusCode(400)
                .when()
                .post(PRODUCT_ENDPOINT)
                .prettyPeek()
                .jsonPath()
                .get("id");
    }

    @Test
    void postProductNegativeCategoryIsNullTest() {
        product.setCategoryTitle(null);

        id = given()
                .body(product.toString())
                .header("Content-Type", "application/json")
                .log()
                .method()
                .log()
                .uri()
                .log()
                .headers()
                .log()
                .body()
                .expect()
                .statusCode(400)
                .when()
                .post(PRODUCT_ENDPOINT)
                .prettyPeek()
                .jsonPath()
                .get("id");
    }

    @Test
    void postProductNegativeCategoryIsNotExistTest() {
        product.setCategoryTitle("jhg");

        id = given()
                .body(product.toString())
                .header("Content-Type", "application/json")
                .log()
                .method()
                .log()
                .uri()
                .log()
                .headers()
                .log()
                .body()
                .expect()
                .statusCode(400)
                .when()
                .post(PRODUCT_ENDPOINT)
                .prettyPeek()
                .jsonPath()
                .get("id");
    }

    @AfterEach
    void tearDown(TestInfo testInfo) {
        if(testInfo.getTags().contains("SkipCleanup")) {
            return;
        }

        when()
                .delete("products/{id}", id)
                .prettyPeek()
                .then()
                .statusCode(200);
    }
}