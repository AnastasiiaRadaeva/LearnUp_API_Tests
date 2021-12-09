package ru.anrad.learnup.enams;

import lombok.Getter;

public enum CategoryType {
    FOOD(1, "Food"),
    ELECTRONIC(2, "Electronic");

    @Getter
    private int id;
    @Getter
    private String name;
    CategoryType( int id,  String name) {
        this.id = id;
        this.name = name;
    }
}
