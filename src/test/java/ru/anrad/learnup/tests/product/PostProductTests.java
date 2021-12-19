package ru.anrad.learnup.tests.product;

import org.junit.jupiter.api.*;
import ru.anrad.learnup.dto.Product;
import ru.anrad.learnup.tests.BaseTests;

import static io.restassured.RestAssured.given;
import static ru.anrad.learnup.Endpoints.*;
import static ru.anrad.learnup.asserts.CommonAsserts.postProductPositiveAsserts;
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
                .categoryTitle(NEW_PRODUCT.getCategory())
                .id(null)
                .build();

        requestSpecification
                .body(product)
                .contentType(HEADER_CT);
    }

    @Test
    void postProductPositiveTest() {
        Product response = given()
                .response()
                .spec(createdResponseSpecification)
                .when()
                .post(POST_PRODUCT_ENDPOINT)
                .prettyPeek()
                .body()
                .as(Product.class);
        id = postProductPositiveAsserts(response, product);
    }

    @Test
    void postProductNegativeProductIsWrongStructureTest() {
        requestSpecification.body(LETTERS_STRING);

        given()
                .response()
                .spec(badReqResponseSpecification)
                .when()
                .post(POST_PRODUCT_ENDPOINT)
                .prettyPeek();
    }

    @Test
    void postProductNegativeIdIsNotNullTest() {
        product.setId(POSITIVE_NUM);
        requestSpecification.body(product);

        id = given()
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
        requestSpecification.body(product);

        id = given()
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
        requestSpecification.body(product);

        id = given()
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
        requestSpecification.body(product);

        id = given()
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
        requestSpecification.body(product);

        id = given()
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
        requestSpecification.body(product);

        id = given()
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
        requestSpecification.body(product);

        id = given()
                .response()
                .spec(badReqResponseSpecification)
                .when()
                .post(POST_PRODUCT_ENDPOINT)
                .prettyPeek()
                .jsonPath()
                .get("id");
    }

    @Test
    void postProductNegativeXSSTest() {
        product.setTitle(XSS_STRING);
        requestSpecification.body(product);

        id = given()
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