package ru.anrad.learnup.enams;

import lombok.Getter;

public enum ProductList {
    BANANA(17700, "Banana", 0, "Food"),
    BREAD(18726, "Bread", 300, "Food"),
    CHANGED_BREAD(18676, "Black Bread", 200, "Food"),
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
