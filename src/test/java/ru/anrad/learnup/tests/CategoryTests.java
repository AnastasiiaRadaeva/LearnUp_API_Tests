package ru.anrad.learnup.tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class CategoryTests {
    public static final String CATEGORY_ENDPOINT = "categories/{id}";
    static Properties properties = new Properties();


    @BeforeAll
    static void setUp() throws IOException {
        properties.load(new FileInputStream("src/test/resources/application.properties"));
        RestAssured.baseURI = properties.getProperty("baseURL");
    }

    @Test
    void getCategoryPositiveTestFirst() {
        Response response = given()
                .when()
                .log()
                .method()
                .log()
                .uri()
                .log()
                .body()
                .when()
                .get(CATEGORY_ENDPOINT, 1)
                .prettyPeek();
        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.body().jsonPath().get("id"), equalTo(1));
        assertThat(response.body().jsonPath().get("products[0].categoryTitle"), equalTo("Food"));
    }

    @Test
    void getCategoryPositiveTestSecond() {
        Response response = given()
                .when()
                .log()
                .method()
                .log()
                .uri()
                .log()
                .body()
                .when()
                .get(CATEGORY_ENDPOINT, 2)
                .prettyPeek();
        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.body().jsonPath().get("id"), equalTo(2));
        assertThat(response.body().jsonPath().get("products[0].categoryTitle"), equalTo("Electronic"));
    }

    @Test
    void getCategoryNegativeIdIsNotExistTest() {
        given()
                .when()
                .log()
                .method()
                .log()
                .uri()
                .log()
                .body()
                .when()
                .get(CATEGORY_ENDPOINT, 999)
                .prettyPeek()
                .then()
                .statusCode(404);
    }

    @Test
    void getCategoryNegativeIdIsNegativeTest() {
        given()
                .when()
                .log()
                .method()
                .log()
                .uri()
                .log()
                .body()
                .when()
                .get(CATEGORY_ENDPOINT, -999)
                .prettyPeek()
                .then()
                .statusCode(400);
    }

    @Test
    void getCategoryNegativeIdIsSymbolsTest() {
        given()
                .when()
                .log()
                .method()
                .log()
                .uri()
                .log()
                .body()
                .when()
                .get(CATEGORY_ENDPOINT, "jku")
                .prettyPeek()
                .then()
                .statusCode(400);
    }

    @Test
    void getCategoryNegativeIdIsRationalCommaTest() {
        given()
                .when()
                .log()
                .method()
                .log()
                .uri()
                .log()
                .body()
                .when()
                .get(CATEGORY_ENDPOINT, "9,7")
                .prettyPeek()
                .then()
                .statusCode(400);
    }

    @Test
    void getCategoryNegativeIdIsRationalPointTest() {
        given()
                .when()
                .log()
                .method()
                .log()
                .uri()
                .log()
                .body()
                .when()
                .get(CATEGORY_ENDPOINT, "9.7")
                .prettyPeek()
                .then()
                .statusCode(400);
    }

    @Test
    void getCategoryNegativeIdIsEmptyTest() {
        given()
                .when()
                .log()
                .method()
                .log()
                .uri()
                .log()
                .body()
                .when()
                .get(CATEGORY_ENDPOINT, "")
                .prettyPeek()
                .then()
                .statusCode(400);
    }

    @Test
    void getCategoryNegativeSQLInjectionCommentTest() {
        given()
                .when()
                .log()
                .method()
                .log()
                .uri()
                .log()
                .body()
                .when()
                .get(CATEGORY_ENDPOINT, "1 -- hello ")
                .prettyPeek()
                .then()
                .statusCode(400);
    }

    @Test
    void getCategoryNegativeXSSTest() {
        given()
                .when()
                .log()
                .method()
                .log()
                .uri()
                .log()
                .body()
                .when()
                .get(CATEGORY_ENDPOINT, "<script>alert(Hello, World!)</script>")
                .prettyPeek()
                .then()
                .statusCode(400)
                .contentType(ContentType.JSON);
    }
}
