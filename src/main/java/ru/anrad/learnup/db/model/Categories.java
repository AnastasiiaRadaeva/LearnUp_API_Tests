package ru.anrad.learnup.db.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Categories {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column categories.id
     *
     * @mbg.generated Thu Dec 23 10:07:02 MSK 2021
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column categories.title
     *
     * @mbg.generated Thu Dec 23 10:07:02 MSK 2021
     */
    private String title;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column categories.id
     *
     * @return the value of categories.id
     *
     * @mbg.generated Thu Dec 23 10:07:02 MSK 2021
     */
}