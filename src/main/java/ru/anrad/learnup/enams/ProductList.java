package ru.anrad.learnup.enams;

import lombok.Getter;

public enum ProductList {
    BREAD(17563, "Bread", 100, "Food"),
    BANANA(17629, "Banana", 100, "Food");

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
