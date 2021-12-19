package ru.anrad.learnup.tests.product;

import org.junit.jupiter.api.Test;
import ru.anrad.learnup.dto.Product;
import ru.anrad.learnup.tests.BaseTests;

import static io.restassured.RestAssured.given;
import static ru.anrad.learnup.Endpoints.*;
import static ru.anrad.learnup.asserts.CommonAsserts.getProductPositiveAsserts;
import static ru.anrad.learnup.enams.ProductList.BANANA;
import static ru.anrad.learnup.enams.ProductList.BREAD;

public class GetProductTests extends BaseTests {

    @Test
    void getAllProductsPositiveTest() {
        given()
                .when()
                .get(POST_PRODUCT_ENDPOINT)
                .prettyPeek();
    }

    @Test
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
    void getProductNegativeIdIsNotExistedTest() {
        given()
                .response()
                .spec(notFoundResponseSpecification)
                .when()
                .get(GET_PRODUCT_ENDPOINT, ID_IS_NOT_EXIST)
                .prettyPeek();
    }

    @Test
    void getProductNegativeIdIsSymbolTest() {
        given()
                .response()
                .spec(badReqResponseSpecification)
                .when()
                .get(GET_PRODUCT_ENDPOINT, LETTERS_STRING)
                .prettyPeek();
    }

    @Test
    void getProductNegativeIdIsNegativeTest() {
        given()
                .response()
                .spec(badReqResponseSpecification)
                .when()
                .get(GET_PRODUCT_ENDPOINT, NEGATIVE_NUM)
                .prettyPeek();
    }

    @Test
    void getProductNegativeIdIsRationalWithPointTest() {
        given()
                .response()
                .spec(badReqResponseSpecification)
                .when()
                .get(GET_PRODUCT_ENDPOINT, RATIONAL_NUM_POINT)
                .prettyPeek();
    }

    @Test
    void getProductNegativeIdIsRationalWithCommaTest() {
        given()
                .response()
                .spec(badReqResponseSpecification)
                .when()
                .get(GET_PRODUCT_ENDPOINT, RATIONAL_NUM_COMMA)
                .prettyPeek();
    }

    @Test
    void getProductNegativeSQLInjectionCommentTest() {
        given()
                .response()
                .spec(badReqResponseSpecification)
                .when()
                .get(GET_PRODUCT_ENDPOINT, SQL_COMMENT)
                .prettyPeek();
    }

    @Test
    void getProductNegativeXSSTest() {
        given()
                .response()
                .spec(badReqResponseSpecification)
                .when()
                .get(GET_PRODUCT_ENDPOINT, XSS_STRING)
                .prettyPeek();
    }
}
