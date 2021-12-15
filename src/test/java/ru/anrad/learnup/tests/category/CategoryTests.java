package ru.anrad.learnup.tests.category;

import org.junit.jupiter.api.Test;
import ru.anrad.learnup.dto.Category;
import ru.anrad.learnup.tests.BaseTests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.anrad.learnup.Endpoints.*;
import static ru.anrad.learnup.enams.CategoryType.FOOD;

public class CategoryTests extends BaseTests {

    @Test
    void getCategoryPositiveIdIsExistsTest() {
        Category response = given()
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
                .response()
                .spec(badReqResponseSpecification)
                .when()
                .get(CATEGORY_ENDPOINT, EMPTY_STRING);
    }

    @Test
    void getCategoryNegativeIdIsNegativeTest() {
        given()
                .response()
                .spec(badReqResponseSpecification)
                .when()
                .get(CATEGORY_ENDPOINT, NEGATIVE_NUM);
    }

    @Test
    void getCategoryNegativeIdIsRationalPointTest() {
        given()
                .response()
                .spec(badReqResponseSpecification)
                .when()
                .get(CATEGORY_ENDPOINT, RATIONAL_NUM_POINT)
                .prettyPeek();
    }

    @Test
    void getCategoryNegativeIdIsRationalCommaTest() {
        given()
                .response()
                .spec(badReqResponseSpecification)
                .when()
                .get(CATEGORY_ENDPOINT, RATIONAL_NUM_COMMA)
                .prettyPeek();
    }

    @Test
    void getCategoryNegativeIdIsLettersTest() {
        given()
                .response()
                .spec(badReqResponseSpecification)
                .when()
                .get(CATEGORY_ENDPOINT, LETTERS_STRING)
                .prettyPeek();
    }
}
