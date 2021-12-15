package ru.anrad.learnup.tests.product;

import org.junit.jupiter.api.*;
import ru.anrad.learnup.dto.Product;
import ru.anrad.learnup.tests.BaseTests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static ru.anrad.learnup.Endpoints.*;
import static ru.anrad.learnup.enams.ProductList.NEW_PRODUCT;

import com.github.javafaker.Faker;


public class PostProductTests extends BaseTests {
    Faker faker = new Faker();
    static Product product;
    Integer id;

    @BeforeEach
    void init_product() {
        product = Product.builder()
                .price(NEW_PRODUCT.getPrice())
                .title(faker.food().dish())
                .categoryTitle(NEW_PRODUCT.getTitle())
                .id(null)
                .build();
    }

    @Test
    void postProductPositiveTest() {
        Product response = given()
                .body(product)
                .header(HEADER_CT_NAME, HEADER_CT)
                .response()
                .spec(createdResponseSpecification)
                .when()
                .post(POST_PRODUCT_ENDPOINT)
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
    void postProductNegativeProductIsWrongStructureTest() {
        given()
                .body(LETTERS_STRING)
                .header(HEADER_CT_NAME, HEADER_CT)
                .response()
                .spec(badReqResponseSpecification)
                .when()
                .post(POST_PRODUCT_ENDPOINT)
                .prettyPeek();
    }

    @Test
    void postProductNegativeIdIsNotNullTest() {
        product.setId(POSITIVE_NUM);
        id = given()
                .body(product)
                .header(HEADER_CT_NAME, HEADER_CT)
                .response()
                .spec(badReqResponseSpecification)
                .when()
                .post(POST_PRODUCT_ENDPOINT)
                .prettyPeek()
                .jsonPath()
                .get("id");
    }

    @Test
    void postProductNegativeTitleIsNullTest() {
        product.setTitle(null);

        id = given()
                .body(product)
                .header(HEADER_CT_NAME, HEADER_CT)
                .response()
                .spec(badReqResponseSpecification)
                .when()
                .post(POST_PRODUCT_ENDPOINT)
                .prettyPeek()
                .jsonPath()
                .get("id");
    }

    @Test
    void postProductNegativeTitleIsEmptyTest() {
        product.setTitle(EMPTY_STRING);

        id = given()
                .body(product)
                .header(HEADER_CT_NAME, HEADER_CT)
                .response()
                .spec(badReqResponseSpecification)
                .when()
                .post(POST_PRODUCT_ENDPOINT)
                .prettyPeek()
                .jsonPath()
                .get("id");
    }

    @Test
    void postProductNegativePriceIsNullTest() {
        product.setPrice(null);

        id = given()
                .body(product)
                .header(HEADER_CT_NAME, HEADER_CT)
                .response()
                .spec(badReqResponseSpecification)
                .when()
                .post(POST_PRODUCT_ENDPOINT)
                .prettyPeek()
                .jsonPath()
                .get("id");
    }

    @Test
    void postProductNegativePriceIsNegativeTest() {
        product.setPrice(NEGATIVE_NUM);

        id = given()
                .body(product)
                .header(HEADER_CT_NAME, HEADER_CT)
                .response()
                .spec(badReqResponseSpecification)
                .when()
                .post(POST_PRODUCT_ENDPOINT)
                .prettyPeek()
                .jsonPath()
                .get("id");
    }

    @Test
    void postProductNegativeCategoryIsNullTest() {
        product.setCategoryTitle(null);

        id = given()
                .body(product)
                .header(HEADER_CT_NAME, HEADER_CT)
                .response()
                .spec(badReqResponseSpecification)
                .when()
                .post(POST_PRODUCT_ENDPOINT)
                .prettyPeek()
                .jsonPath()
                .get("id");
    }

    @Test
    void postProductNegativeCategoryIsNotExistTest() {
        product.setCategoryTitle(LETTERS_STRING);

        id = given()
                .body(product)
                .header(HEADER_CT_NAME, HEADER_CT)
                .response()
                .spec(badReqResponseSpecification)
                .when()
                .post(POST_PRODUCT_ENDPOINT)
                .prettyPeek()
                .jsonPath()
                .get("id");
    }

    @AfterEach
    void tearDown(TestInfo testInfo) {
        if (id != null) {
            given()
                    .response()
                    .spec(deleteResponseSpecification)
                    .when()
                    .delete(GET_PRODUCT_ENDPOINT, id)
                    .prettyPeek();
        }
    }
}