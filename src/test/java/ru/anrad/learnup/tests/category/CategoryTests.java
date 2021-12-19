package ru.anrad.learnup.tests.category;

import org.junit.jupiter.api.Test;
import ru.anrad.learnup.dto.Category;
import ru.anrad.learnup.tests.BaseTests;

import static io.restassured.RestAssured.given;
import static ru.anrad.learnup.Endpoints.*;
import static ru.anrad.learnup.asserts.CommonAsserts.getCategoryPositiveAsserts;
import static ru.anrad.learnup.enams.CategoryType.ELECTRONIC;
import static ru.anrad.learnup.enams.CategoryType.FOOD;

public class CategoryTests extends BaseTests {

    @Test
    void getCategoryPositiveIdIsFoodTest() {
        Category response = given()
                .when()
                .get(CATEGORY_ENDPOINT, FOOD.getId())
                .prettyPeek()
                .body().as(Category.class);
        getCategoryPositiveAsserts(response, FOOD);
    }

    @Test
    void getCategoryPositiveIdIsElectronicTest() {
        Category response = given()
                .when()
                .get(CATEGORY_ENDPOINT, ELECTRONIC.getId())
                .prettyPeek()
                .body().as(Category.class);
        getCategoryPositiveAsserts(response, ELECTRONIC);
    }

    @Test
    void getCategoryNegativeIdIsEmptyTest() {
        given()
                .response()
                .spec(badReqResponseSpecification)
                .when()
                .get(CATEGORY_ENDPOINT, EMPTY_STRING)
                .prettyPeek();
    }

    @Test
    void getCategoryNegativeIdIsNegativeTest() {
        given()
                .response()
                .spec(badReqResponseSpecification)
                .when()
                .get(CATEGORY_ENDPOINT, NEGATIVE_NUM)
                .prettyPeek();
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

    @Test
    void getCategoryNegativeSQLInjectionCommentTest() {
        given()
                .response()
                .spec(badReqResponseSpecification)
                .when()
                .get(CATEGORY_ENDPOINT, SQL_COMMENT)
                .prettyPeek();
    }

    @Test
    void getCategoryNegativeXSSTest() {
        given()
                .response()
                .spec(badReqResponseSpecification)
                .when()
                .get(CATEGORY_ENDPOINT, XSS_STRING)
                .prettyPeek();
    }
}
