package com.amazon.amazon_backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity  //tells Spring that this class represents database table
@NoArgsConstructor  //created empty constructor
@Getter // creates getCategory() and getCategory_ID() by itself
public class Category {
    @Id  //tells Spring that this thing is ID and is selfIncreasing
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int CategoryId;

    @Column(nullable = false, unique = true)
    private String Category;

    // this method tells Spring that if you want to create new row you have to give a category name
    public Category(String categoryName) {
        this.Category = categoryName;
    }

}
