package ru.anrad.learnup.tests.product;

import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import ru.anrad.learnup.dto.Product;
import ru.anrad.learnup.tests.BaseTests;

import static io.restassured.RestAssured.given;
import static ru.anrad.learnup.Endpoints.*;
import static ru.anrad.learnup.asserts.CommonAsserts.putProductPositiveAsserts;
import static ru.anrad.learnup.enams.ProductList.CHANGED_BREAD;

@Epic("Tests for products")
@Story("Put Product tests")
@Severity(SeverityLevel.NORMAL)
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
    @Description("Изменить существующий продукт на CHANGED_BREAD")
    void putProductPositiveTest() {
        Product response = given()
                .when()
                .put(POST_PRODUCT_ENDPOINT)
                .prettyPeek()
                .body()
                .as(Product.class);
        id = putProductPositiveAsserts(response, product);
    }

    @Test
    @Description("Изменить существующий продукт на продукт со строковой структурой")
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
    @Description("Изменить несуществующий продукт (id не существует)")
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
    @Description("Изменить существующий продукт на продукт с названием = null")
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
    @Description("Изменить существующий продукт на продукт с пустым названием")
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
    @Description("Изменить существующий продукт на продукт ценой = null")
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
    @Description("Изменить существующий продукт на продукт с отрицательнйо ценой")
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
    @Description("Изменить существующий продукт на продукт с категорией = null")
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
    @Description("Изменить существующий продукт на продукт с несуществующей категорией")
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
    @Description("Проверка на XSS уязвимость - id с js кодом")
    @Severity(SeverityLevel.CRITICAL)
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
    @Step("Проверка наличия продукта в базе")
    void checkProduct(TestInfo testInfo) {
        if (id != null) {
            given()
                    .when()
                    .get(GET_PRODUCT_ENDPOINT, id)
                    .prettyPeek();
        }
    }
}
