package ru.anrad.learnup.enams;

import lombok.Getter;

public enum ProductList {
    BANANA(17629, "Banana", 100, "Food"),
    BREAD(17563, "Bread", 100, "Food"),
    NEW_PRODUCT(0, "", 500, "Food");

    @Getter
    private int id;
    @Getter
    private String title;
    @Getter
    private int price;
    @Getter
    private String category;


    ProductList(int id, String title, int price, String category) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.category = category;
    }
}
