package ru.anrad.learnup.tests.category;

import org.junit.jupiter.api.Test;
import ru.anrad.learnup.dto.Category;
import ru.anrad.learnup.tests.BaseTests;
import io.qameta.allure.*;

import static io.restassured.RestAssured.given;
import static ru.anrad.learnup.Endpoints.*;
import static ru.anrad.learnup.asserts.CommonAsserts.getCategoryPositiveAsserts;
import static ru.anrad.learnup.enams.CategoryType.ELECTRONIC;
import static ru.anrad.learnup.enams.CategoryType.FOOD;

@Epic("Tests for categories")
@Story("Get Category tests")
@Severity(SeverityLevel.NORMAL)
public class CategoryTests extends BaseTests {

    @Test
    @Description("Получить категорию Food")
    void getCategoryPositiveIdIsFoodTest() {
        Category response = given()
                .when()
                .get(CATEGORY_ENDPOINT, FOOD.getId())
                .prettyPeek()
                .body().as(Category.class);
        getCategoryPositiveAsserts(response, FOOD);
    }

    @Test
    @Description("Получить категорию Electronic")
    void getCategoryPositiveIdIsElectronicTest() {
        Category response = given()
                .when()
                .get(CATEGORY_ENDPOINT, ELECTRONIC.getId())
                .prettyPeek()
                .body().as(Category.class);
        getCategoryPositiveAsserts(response, ELECTRONIC);
    }

    @Test
    @Description("Получить категорию по пустой строке")
    void getCategoryNegativeIdIsEmptyTest() {
        given()
                .response()
                .spec(badReqResponseSpecification)
                .when()
                .get(CATEGORY_ENDPOINT, EMPTY_STRING)
                .prettyPeek();
    }

    @Test
    @Description("Получить категорию с негативным id")
    void getCategoryNegativeIdIsNegativeTest() {
        given()
                .response()
                .spec(badReqResponseSpecification)
                .when()
                .get(CATEGORY_ENDPOINT, NEGATIVE_NUM)
                .prettyPeek();
    }

    @Test
    @Description("Получить категорию дробным id (через точку)")
    void getCategoryNegativeIdIsRationalPointTest() {
        given()
                .response()
                .spec(badReqResponseSpecification)
                .when()
                .get(CATEGORY_ENDPOINT, RATIONAL_NUM_POINT)
                .prettyPeek();
    }

    @Test
    @Description("Получить категорию дробным id (через запятую)")
    void getCategoryNegativeIdIsRationalCommaTest() {
        given()
                .response()
                .spec(badReqResponseSpecification)
                .when()
                .get(CATEGORY_ENDPOINT, RATIONAL_NUM_COMMA)
                .prettyPeek();
    }

    @Test
    @Description("Получить категорию буквенным id")
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
