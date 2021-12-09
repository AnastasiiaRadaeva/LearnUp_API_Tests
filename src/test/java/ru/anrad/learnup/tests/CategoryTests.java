package ru.anrad.learnup.tests;

import io.restassured.RestAssured;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.anrad.learnup.dto.Category;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.anrad.learnup.enams.CategoryType.FOOD;

public class CategoryTests {
    public static final String CATEGORY_ENDPOINT = "categories/{id}";
    static Properties properties = new Properties();

    @BeforeAll
    static void setUp() throws IOException {
        properties.load(new FileInputStream("src/test/resources/application.properties"));
        RestAssured.baseURI = properties.getProperty("baseURL");
    }

    @Test
    void getCategoryPositiveIdIsExistsTest() {
        Category response = given()
                .when()
                .log()
                .method()
                .log()
                .uri()
                .log()
                .body()
                .expect()
                .statusCode(200)
                .when()
                .get(CATEGORY_ENDPOINT, FOOD.getId())
                .prettyPeek()
                .body().as(Category.class);
        assertThat(response.getId(), equalTo(FOOD.getId()));
        assertThat(response.getTitle(), equalTo(FOOD.getName()));
        response.getProducts().forEach(
                e -> assertThat(e.getCategoryTitle(), equalTo(FOOD.getName()))
        );
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
                .expect()
                .statusCode(400)
                .when()
                .get(CATEGORY_ENDPOINT, "")
                .prettyPeek();
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
                .expect()
                .statusCode(400)
                .when()
                .get(CATEGORY_ENDPOINT, -7)
                .prettyPeek();
    }

    @Test
    void getCategoryNegativeIdIsFractPointTest() {
        given()
                .when()
                .log()
                .method()
                .log()
                .uri()
                .log()
                .body()
                .expect()
                .statusCode(400)
                .when()
                .get(CATEGORY_ENDPOINT, 8.9)
                .prettyPeek();
    }

    @Test
    void getCategoryNegativeIdIsFractCommaTest() {
        given()
                .when()
                .log()
                .method()
                .log()
                .uri()
                .log()
                .body()
                .expect()
                .statusCode(400)
                .when()
                .get(CATEGORY_ENDPOINT, "8,9")
                .prettyPeek();
    }

    @Test
    void getCategoryNegativeIdIsLettersTest() {
        given()
                .when()
                .log()
                .method()
                .log()
                .uri()
                .log()
                .body()
                .expect()
                .statusCode(400)
                .when()
                .get(CATEGORY_ENDPOINT, "one")
                .prettyPeek();
    }
}
