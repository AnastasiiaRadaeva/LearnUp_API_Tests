package ru.anrad.learnup.tests.product;

import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import ru.anrad.learnup.dto.Product;
import ru.anrad.learnup.tests.BaseTests;

import static io.restassured.RestAssured.given;
import static ru.anrad.learnup.Endpoints.*;
import static ru.anrad.learnup.asserts.CommonAsserts.postProductPositiveAsserts;
import static ru.anrad.learnup.enams.ProductList.NEW_PRODUCT;

import com.github.javafaker.Faker;

@Epic("Tests for products")
@Story("Post Product tests")
@Severity(SeverityLevel.NORMAL)
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
    @Description("Создать новый продукт")
    void postProductPositiveTest() {
        Product response = given()
                .response()
                .spec(createdResponseSpecification)
                .when()
                .post(POST_PRODUCT_ENDPOINT)
                .prettyPeek()
                .body()
                .as(Product.class);
        id = postProductPositiveAsserts(response, product, productsMapper, categoriesMapper);
    }

    @Test
    @Description("Создать новый продукт со структурой - строка")
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
    @Description("Создать новый продукт с id != null")
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
    @Description("Создать новый продукт с названием = null")
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
    @Description("Создать новый продукт с пустым названием")
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
    @Description("Создать новый продукт с ценой = null")
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
    @Description("Создать новый продукт с отрицательной ценой")
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
    @Description("Создать новый продукт с категорией = null")
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
    @Description("Создать новый продукт с несуществующей категорией")
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
    @Description("Проверка на XSS уязвимость - id с js кодом")
    @Severity(SeverityLevel.CRITICAL)
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
    @Step("Удаление созданного продукта")
    void tearDown() {
        if (id != null) {
            productsMapper.deleteByPrimaryKey(id.longValue());
        }
    }
}