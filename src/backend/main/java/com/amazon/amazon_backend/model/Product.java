package com.amazon.amazon_backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "Products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "description", length = 800)
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "seller_id", nullable = false)
    private User seller;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "product_name", nullable = false, length = 300)
    private String productName;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column (name = "img_url", length = 500)
    private String imgUrl;

    public Product(String description, User seller, Category category, String productName, BigDecimal price, Integer quantity, String imgUrl) {
        this.description = description;
        this.seller = seller;
        this.category = category;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.imgUrl = imgUrl;
    }

}
