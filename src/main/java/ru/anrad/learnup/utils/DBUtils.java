package ru.anrad.learnup.utils;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import ru.anrad.learnup.db.dao.CategoriesMapper;
import ru.anrad.learnup.db.dao.ProductsMapper;

import java.io.InputStream;

@UtilityClass //делает класс и методы внутри него статическими
public class DBUtils {
    static String resource = "myBatisConfig.xml";
    @SneakyThrows
    public SqlSession getSession() {
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory =
                new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession(true); //включили автоматический коммит
        System.out.println("Соединение установлено");

        return sqlSession;
    }

    public ProductsMapper getProductsMapper(){
        return getSession().getMapper(ProductsMapper.class);
    }

    public CategoriesMapper getCategoryMapper(){
        return getSession().getMapper(CategoriesMapper.class);
    }
}
