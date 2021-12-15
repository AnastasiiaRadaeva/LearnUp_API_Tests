package ru.anrad.learnup.tests.product;

import org.junit.jupiter.api.Test;
import ru.anrad.learnup.dto.Product;
import ru.anrad.learnup.tests.BaseTests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.anrad.learnup.Endpoints.*;
import static ru.anrad.learnup.enams.ProductList.BANANA;

public class GetProductTests extends BaseTests {

    @Test
    void getAllProductsPositiveTest() {
        given()
                .when()
                .get(POST_PRODUCT_ENDPOINT)
                .prettyPeek();
    }

    @Test
    void getProductPositiveTest() {
        Product response = given()
                .when()
                .get(GET_PRODUCT_ENDPOINT, BANANA.getId())
                .prettyPeek()
                .body()
                .as(Product.class);
        assertThat(response.getId(), equalTo(BANANA.getId()));
        assertThat(response.getTitle(), equalTo(BANANA.getTitle()));
        assertThat(response.getPrice(), equalTo(BANANA.getPrice()));
        assertThat(response.getCategoryTitle(), equalTo(BANANA.getCategory()));
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
}
