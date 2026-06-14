package com.amazon.amazon_backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int CategoryId;

    @Column(nullable = false, unique = true)
    private String Category;

    public Category(String categoryName) {
        this.Category = categoryName;
    }

}
