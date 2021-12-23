package ru.anrad.learnup.asserts;

import io.qameta.allure.Step;
import lombok.experimental.UtilityClass;
import ru.anrad.learnup.db.dao.CategoriesMapper;
import ru.anrad.learnup.db.dao.ProductsMapper;
import ru.anrad.learnup.db.model.Categories;
import ru.anrad.learnup.db.model.Products;
import ru.anrad.learnup.dto.Category;
import ru.anrad.learnup.dto.Product;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@UtilityClass
public class CommonAsserts {
    @Step("Проверка соответствия полученной категории запрашиваемой")
    public void getCategoryPositiveAsserts(Category response, Categories category) {
        assertThat(response.getId(), equalTo(category.getId()));
        assertThat(response.getTitle(), equalTo(category.getTitle()));
        response.getProducts().forEach(
                e -> {
                    assertThat(e.getCategoryTitle(), equalTo(category.getTitle()));
                }
        );
    }

    @Step("Проверка соответствия полученного продукта запрашиваемому")
    public void getProductPositiveAsserts(Product response, Products product, CategoriesMapper categoriesMapper) {
        assertThat(Long.valueOf(response.getId()), equalTo(product.getId()));
        assertThat(response.getTitle(), equalTo(product.getTitle()));
        assertThat(response.getPrice(), equalTo(product.getPrice()));
        assertThat(response.getCategoryTitle(), equalTo(categoriesMapper.selectByPrimaryKey(Math.toIntExact(product.getCategory_id())).getTitle()));
    }

    @Step("Проверка соответствия созданного продукта поданному в запросе")
    public Integer postProductPositiveAsserts(Product response, Product product, ProductsMapper productsMapper, CategoriesMapper categoriesMapper) {
        Integer id = response.getId();
        assertThat(response.getTitle(), equalTo(product.getTitle()));
        assertThat(response.getPrice(), equalTo(product.getPrice()));
        assertThat(response.getCategoryTitle(), equalTo(product.getCategoryTitle()));

        if (id != null) {
            Products productDB = productsMapper.selectByPrimaryKey(Long.valueOf(id));
            assertThat(response.getTitle(), equalTo(productDB.getTitle()));
            assertThat(response.getPrice(), equalTo(productDB.getPrice()));
            assertThat(response.getCategoryTitle(), equalTo(categoriesMapper.selectByPrimaryKey(Math.toIntExact(productDB.getCategory_id())).getTitle()));
        }
        return id;
    }

    @Step("Проверка соответствия измененного продукта поданному в запросе")
    public Integer putProductPositiveAsserts(Product response, Product product, ProductsMapper productsMapper, CategoriesMapper categoriesMapper) {
        Integer id = response.getId();
        assertThat(response.getId(), equalTo(product.getId()));
        assertThat(response.getTitle(), equalTo(product.getTitle()));
        assertThat(response.getPrice(), equalTo(product.getPrice()));
        assertThat(response.getCategoryTitle(), equalTo(product.getCategoryTitle()));

        if (id != null) {
            Products productDB = productsMapper.selectByPrimaryKey(Long.valueOf(id));
            assertThat(response.getTitle(), equalTo(productDB.getTitle()));
            assertThat(response.getPrice(), equalTo(productDB.getPrice()));
            assertThat(response.getCategoryTitle(), equalTo(categoriesMapper.selectByPrimaryKey(Math.toIntExact(productDB.getCategory_id())).getTitle()));
        }

        return id;
    }
}
