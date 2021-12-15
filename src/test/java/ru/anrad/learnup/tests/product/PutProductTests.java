package ru.anrad.learnup.tests.product;

import org.junit.jupiter.api.*;
import ru.anrad.learnup.dto.Product;
import ru.anrad.learnup.tests.BaseTests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static ru.anrad.learnup.Endpoints.*;
import static ru.anrad.learnup.enams.ProductList.CHANGED_BREAD;

public class PutProductTests extends BaseTests {
    static Product product;
    Integer id;

    @BeforeEach
    void init_product() {
        product = Product.builder()
                .id(CHANGED_BREAD.getId())
                .price(CHANGED_BREAD.getPrice())
                .title(CHANGED_BREAD.getTitle())
                .categoryTitle(CHANGED_BREAD.getCategory())
                .build();
    }

    @Test
    void putProductPositiveTest() {
        Product response = given()
                .body(product)
                .header(HEADER_CT_NAME, HEADER_CT)
                .when()
                .put(POST_PRODUCT_ENDPOINT)
                .prettyPeek()
                .body()
                .as(Product.class);
        id = response.getId();
        assertThat(response.getId(), is(not(nullValue())));
        assertThat(response.getTitle(), equalTo(product.getTitle()));
        assertThat(response.getPrice(), equalTo(product.getPrice()));
        assertThat(response.getCategoryTitle(), equalTo(product.getCategoryTitle()));
    }

    @Test
    void putProductNegativeProductIsWrongStructureTest() {
        given()
                .body(LETTERS_STRING)
                .header(HEADER_CT_NAME, HEADER_CT)
                .response()
                .spec(badReqResponseSpecification)
                .when()
                .put(POST_PRODUCT_ENDPOINT)
                .prettyPeek();
    }

    @Test
    void putProductNegativeIdIsNotExistTest() {
        product.setId(POSITIVE_NUM);
        id = given()
                .body(product)
                .header(HEADER_CT_NAME, HEADER_CT)
                .response()
                .spec(badReqResponseSpecification)
                .when()
                .put(POST_PRODUCT_ENDPOINT)
                .prettyPeek()
                .jsonPath()
                .get("id");
    }

    @Test
    void putProductNegativeTitleIsNullTest() {
        product.setTitle(null);

        id = given()
                .body(product)
                .header(HEADER_CT_NAME, HEADER_CT)
                .response()
                .spec(badReqResponseSpecification)
                .when()
                .put(POST_PRODUCT_ENDPOINT)
                .prettyPeek()
                .jsonPath()
                .get("id");
    }

    @Test
    void putProductNegativeTitleIsEmptyTest() {
        product.setTitle(EMPTY_STRING);

        id = given()
                .body(product)
                .header(HEADER_CT_NAME, HEADER_CT)
                .response()
                .spec(badReqResponseSpecification)
                .when()
                .put(POST_PRODUCT_ENDPOINT)
                .prettyPeek()
                .jsonPath()
                .get("id");
    }

    @Test
    void putProductNegativePriceIsNullTest() {
        product.setPrice(null);

        id = given()
                .body(product)
                .header(HEADER_CT_NAME, HEADER_CT)
                .response()
                .spec(badReqResponseSpecification)
                .when()
                .put(POST_PRODUCT_ENDPOINT)
                .prettyPeek()
                .jsonPath()
                .get("id");
    }

    @Test
    void putProductNegativePriceIsNegativeTest() {
        product.setPrice(NEGATIVE_NUM);

        id = given()
                .body(product)
                .header(HEADER_CT_NAME, HEADER_CT)
                .response()
                .spec(badReqResponseSpecification)
                .when()
                .put(POST_PRODUCT_ENDPOINT)
                .prettyPeek()
                .jsonPath()
                .get("id");
    }

    @Test
    void putProductNegativeCategoryIsNullTest() {
        product.setCategoryTitle(null);

        id = given()
                .body(product)
                .header(HEADER_CT_NAME, HEADER_CT)
                .response()
                .spec(badReqResponseSpecification)
                .when()
                .put(POST_PRODUCT_ENDPOINT)
                .prettyPeek()
                .jsonPath()
                .get("id");
    }

    @Test
    void putProductNegativeCategoryIsNotExistTest() {
        product.setCategoryTitle(LETTERS_STRING);

        id = given()
                .body(product)
                .header(HEADER_CT_NAME, HEADER_CT)
                .response()
                .spec(badReqResponseSpecification)
                .when()
                .put(POST_PRODUCT_ENDPOINT)
                .prettyPeek()
                .jsonPath()
                .get("id");
    }

    @AfterEach
    void checkProduct(TestInfo testInfo) {
        if (id != null) {
            given()
                    .when()
                    .get(GET_PRODUCT_ENDPOINT, id)
                    .prettyPeek();
        }
    }
}
