package ru.anrad.learnup;

import lombok.SneakyThrows;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import ru.anrad.learnup.db.dao.ProductsMapper;
import ru.anrad.learnup.db.model.Products;
import ru.anrad.learnup.db.model.ProductsExample;

import java.io.InputStream;

public class Main {
    static String resource = "myBatisConfig.xml";

    @SneakyThrows
    public static void main(String[] args) {
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory =
                new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession(true); //включили автоматический коммит
        System.out.println("Соединение установлено");

        ProductsMapper productsMapper = sqlSession.getMapper(ProductsMapper.class);
        Products productByPrKey = productsMapper.selectByPrimaryKey(18726l);
        System.out.println(productByPrKey.getTitle());

        Products product = Products.builder()
                .category_id(1L)
                .price(200)
                .title("Berries")
                .build();
        productsMapper.insert(product);
        ProductsExample productsExample = new ProductsExample();
        productsExample.createCriteria()
                .andCategory_idEqualTo(product.getCategory_id())
                .andPriceEqualTo(product.getPrice())
                .andTitleEqualTo(product.getTitle());
        Long productId = productsMapper.selectByExample(productsExample).get(0).getId();
        System.out.println(productId);
        productsMapper.deleteByPrimaryKey(productId);
    }
}
