package ru.anrad.learnup.tests;

import ru.anrad.learnup.dto.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class PostProductTests {
    Product product;
    Integer id;
    @BeforeEach
    void setUp() {
        product = Product.builder()
                .price(100)
                .title("Banana")
                .categoryTitle("Food")
                .build();
    }


    @Test
    void postProductPositiveTest() {
        id = given()
                .body(product.toString())
                .header("Content-Type", "application/json")
                .log()
                .all()
                .expect()
                .statusCode(201)
                .when()
                .post("http://80.78.248.82:8189/market/api/v1/products")
                .prettyPeek()
                .jsonPath()
                .get("id");
    }

    @AfterEach
    void tearDown() {
        when()
                .delete("http://80.78.248.82:8189/market/api/v1/products/{id}", id)
                .prettyPeek()
                .then()
                .statusCode(200);
    }
}