package ru.anrad.learnup;

public class Endpoints {
    public static final String CATEGORY_ENDPOINT = "categories/{id}";
    public static final String POST_PRODUCT_ENDPOINT = "products";
    public static final String GET_PRODUCT_ENDPOINT = "products/{id}";

    public static final Integer ID_IS_NOT_EXIST = 1;

    public static final String EMPTY_STRING = "";
    public static final Integer POSITIVE_NUM = 8;
    public static final Integer NEGATIVE_NUM = -7;
    public static final String RATIONAL_NUM_POINT = "8.9";
    public static final String RATIONAL_NUM_COMMA = "8,9";
    public static final String LETTERS_STRING = "string";
    public static final String SQL_COMMENT = "1 -- hello";
    public static final String XSS_STRING = "<script>alert(Hello, World!)</script>";

    public static final String HEADER_CT = "application/json";

    //Category types id
    public static Integer FOOD_ID = 1;
    public static Integer ELECTRONIC_ID = 2;
    //Products id
    public static Integer BANANA_ID = 17700;
    public static Integer BREAD_ID = 18726;

}
