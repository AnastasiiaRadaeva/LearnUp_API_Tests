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

        requestSpecification
                .body(product)
                .contentType(HEADER_CT);
    }

    @Test
    void putProductPositiveTest() {
        Product response = given()
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
        requestSpecification.body(LETTERS_STRING);

        given()
                .response()
                .spec(badReqResponseSpecification)
                .when()
                .put(POST_PRODUCT_ENDPOINT)
                .prettyPeek();
    }

    @Test
    void putProductNegativeIdIsNotExistTest() {
        product.setId(POSITIVE_NUM);
        requestSpecification.body(product);

        id = given()
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
        requestSpecification.body(product);

        id = given()
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
        requestSpecification.body(product);

        id = given()
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
        requestSpecification.body(product);

        id = given()
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
        requestSpecification.body(product);

        id = given()
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
        requestSpecification.body(product);

        id = given()
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
        requestSpecification.body(product);

        id = given()
                .response()
                .spec(badReqResponseSpecification)
                .when()
                .put(POST_PRODUCT_ENDPOINT)
                .prettyPeek()
                .jsonPath()
                .get("id");
    }

    @Test
    void putProductNegativeXSSTest() {
        product.setTitle(XSS_STRING);
        requestSpecification.body(product);

        id = given()
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
