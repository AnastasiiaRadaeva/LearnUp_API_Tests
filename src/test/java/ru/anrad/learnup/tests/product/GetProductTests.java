package ru.anrad.learnup.tests.product;

import io.qameta.allure.*;
import org.junit.jupiter.api.Test;
import ru.anrad.learnup.dto.Product;
import ru.anrad.learnup.tests.BaseTests;

import static io.restassured.RestAssured.given;
import static ru.anrad.learnup.Endpoints.*;
import static ru.anrad.learnup.asserts.CommonAsserts.getProductPositiveAsserts;
import static ru.anrad.learnup.enams.ProductList.BANANA;
import static ru.anrad.learnup.enams.ProductList.BREAD;

@Epic("Tests for products")
@Story("Get Product tests")
@Severity(SeverityLevel.NORMAL)
public class GetProductTests extends BaseTests {

    @Test
    @Description("Получить все продукты")
    void getAllProductsPositiveTest() {
        given()
                .when()
                .get(POST_PRODUCT_ENDPOINT)
                .prettyPeek();
    }

    @Test
    @Description("Получить продукт BANANA")
    void getProductPositiveProductIsBananaTest() {
        Product response = given()
                .when()
                .get(GET_PRODUCT_ENDPOINT, BANANA.getId())
                .prettyPeek()
                .body()
                .as(Product.class);
        getProductPositiveAsserts(response, BANANA);
    }

    @Test
    @Description("Получить продукт BREAD")
    void getProductPositiveProductIsBreadTest() {
        Product response = given()
                .when()
                .get(GET_PRODUCT_ENDPOINT, BREAD.getId())
                .prettyPeek()
                .body()
                .as(Product.class);
        getProductPositiveAsserts(response, BREAD);
    }

    @Test
    @Description("Получить несуществующий продукт")
    void getProductNegativeIdIsNotExistedTest() {
        given()
                .response()
                .spec(notFoundResponseSpecification)
                .when()
                .get(GET_PRODUCT_ENDPOINT, ID_IS_NOT_EXIST)
                .prettyPeek();
    }

    @Test
    @Description("Получить продукт с буквенным id")
    void getProductNegativeIdIsSymbolTest() {
        given()
                .response()
                .spec(badReqResponseSpecification)
                .when()
                .get(GET_PRODUCT_ENDPOINT, LETTERS_STRING)
                .prettyPeek();
    }

    @Test
    @Description("Получить продукт с отрицательным id")
    void getProductNegativeIdIsNegativeTest() {
        given()
                .response()
                .spec(badReqResponseSpecification)
                .when()
                .get(GET_PRODUCT_ENDPOINT, NEGATIVE_NUM)
                .prettyPeek();
    }

    @Test
    @Description("Получить продукт с дробным id (через точку)")
    void getProductNegativeIdIsRationalWithPointTest() {
        given()
                .response()
                .spec(badReqResponseSpecification)
                .when()
                .get(GET_PRODUCT_ENDPOINT, RATIONAL_NUM_POINT)
                .prettyPeek();
    }

    @Test
    @Description("Получить продукт с дробным id (через запятую)")
    void getProductNegativeIdIsRationalWithCommaTest() {
        given()
                .response()
                .spec(badReqResponseSpecification)
                .when()
                .get(GET_PRODUCT_ENDPOINT, RATIONAL_NUM_COMMA)
                .prettyPeek();
    }

    @Test
    @Description("Проверка на SQL-инъекции - id с SQL комментарием")
    @Severity(SeverityLevel.CRITICAL)
    void getProductNegativeSQLInjectionCommentTest() {
        given()
                .response()
                .spec(badReqResponseSpecification)
                .when()
                .get(GET_PRODUCT_ENDPOINT, SQL_COMMENT)
                .prettyPeek();
    }

    @Test
    @Description("Проверка на XSS уязвимость - id с js кодом")
    @Severity(SeverityLevel.CRITICAL)
    void getProductNegativeXSSTest() {
        given()
                .response()
                .spec(badReqResponseSpecification)
                .when()
                .get(GET_PRODUCT_ENDPOINT, XSS_STRING)
                .prettyPeek();
    }
}
