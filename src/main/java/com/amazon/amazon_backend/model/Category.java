package com.amazon.amazon_backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Categories")
@NoArgsConstructor
@Getter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Category_ID")
    private Integer categoryId;

    @Column(name = "Category", nullable = false, unique = true)
    private String categoryName;

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

}
