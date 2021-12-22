package ru.anrad.learnup.asserts;

import io.qameta.allure.Step;
import lombok.experimental.UtilityClass;
import ru.anrad.learnup.dto.Category;
import ru.anrad.learnup.dto.Product;
import ru.anrad.learnup.enams.CategoryType;
import ru.anrad.learnup.enams.ProductList;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.anrad.learnup.asserts.IsCategoryExists.isCategoryExists;

@UtilityClass
public class CommonAsserts {
    @Step("Проверка соответствия полученной категории запрашиваемой")
    public void getCategoryPositiveAsserts(Category response, CategoryType category) {
        assertThat(response.getId(), equalTo(category.getId()));
        assertThat(response.getTitle(), equalTo(category.getName()));
        response.getProducts().forEach(
                e -> {
                    assertThat(e.getCategoryTitle(), isCategoryExists());
                    assertThat(e.getCategoryTitle(), equalTo(category.getName()));
                }
        );
    }

    @Step("Проверка соответствия полученного продукта запрашиваемому")
    public void getProductPositiveAsserts(Product response, ProductList product) {
        assertThat(response.getId(), equalTo(product.getId()));
        assertThat(response.getTitle(), equalTo(product.getTitle()));
        assertThat(response.getPrice(), equalTo(product.getPrice()));
        assertThat(response.getCategoryTitle(), equalTo(product.getCategory()));
    }

    @Step("Проверка соответствия созданного продукта поданному в запросе")
    public Integer postProductPositiveAsserts(Product response, Product product) {
        Integer id = response.getId();
        assertThat(response.getId(), is(not(nullValue())));
        assertThat(response.getTitle(), equalTo(product.getTitle()));
        assertThat(response.getPrice(), equalTo(product.getPrice()));
        assertThat(response.getCategoryTitle(), equalTo(product.getCategoryTitle()));
        return id;
    }

    @Step("Проверка соответствия измененного продукта поданному в запросе")
    public Integer putProductPositiveAsserts(Product response, Product product) {
        Integer id = response.getId();
        assertThat(response.getId(), equalTo(product.getId()));
        assertThat(response.getTitle(), equalTo(product.getTitle()));
        assertThat(response.getPrice(), equalTo(product.getPrice()));
        assertThat(response.getCategoryTitle(), equalTo(product.getCategoryTitle()));
        return id;
    }
}
