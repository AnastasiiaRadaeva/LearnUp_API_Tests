package ru.anrad.learnup.tests.category;

import org.junit.jupiter.api.Test;
import ru.anrad.learnup.dto.Category;
import ru.anrad.learnup.tests.BaseTests;
import io.qameta.allure.*;

import static io.restassured.RestAssured.given;
import static ru.anrad.learnup.Endpoints.*;
import static ru.anrad.learnup.asserts.CommonAsserts.getCategoryPositiveAsserts;
import static ru.anrad.learnup.Endpoints.ELECTRONIC_ID;
import static ru.anrad.learnup.Endpoints.FOOD_ID;

@Epic("Tests for categories")
@Story("Get Category tests")
@Severity(SeverityLevel.MINOR)
public class CategoryTests extends BaseTests {

    @Test
    @Description("Получить категорию Food")
    void getCategoryPositiveIdIsFoodTest() {
        Category response = given()
                .when()
                .get(CATEGORY_ENDPOINT, FOOD_ID)
                .prettyPeek()
                .body().as(Category.class);
        getCategoryPositiveAsserts(response, categoriesMapper.selectByPrimaryKey(FOOD_ID));
    }

    @Test
    @Description("Получить категорию Electronic")
    void getCategoryPositiveIdIsElectronicTest() {
        Category response = given()
                .when()
                .get(CATEGORY_ENDPOINT, ELECTRONIC_ID)
                .prettyPeek()
                .body().as(Category.class);
        getCategoryPositiveAsserts(response, categoriesMapper.selectByPrimaryKey(ELECTRONIC_ID));
    }

    @Test
    @Description("Получить категорию по id - пустая строка")
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
    @Description("Получить категорию с буквенным id")
    void getCategoryNegativeIdIsLettersTest() {
        given()
                .response()
                .spec(badReqResponseSpecification)
                .when()
                .get(CATEGORY_ENDPOINT, LETTERS_STRING)
                .prettyPeek();
    }

    @Test
    @Description("Проверка на SQL-инъекции - id с SQL комментарием")
    @Severity(SeverityLevel.CRITICAL)
    void getCategoryNegativeSQLInjectionCommentTest() {
        given()
                .response()
                .spec(badReqResponseSpecification)
                .when()
                .get(CATEGORY_ENDPOINT, SQL_COMMENT)
                .prettyPeek();
    }

    @Test
    @Description("Проверка на XSS уязвимость - id с js кодом")
    @Severity(SeverityLevel.CRITICAL)
    void getCategoryNegativeXSSTest() {
        given()
                .response()
                .spec(badReqResponseSpecification)
                .when()
                .get(CATEGORY_ENDPOINT, XSS_STRING)
                .prettyPeek();
    }
}
