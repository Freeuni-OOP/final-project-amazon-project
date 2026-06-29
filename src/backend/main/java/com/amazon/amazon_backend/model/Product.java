package com.amazon.amazon_backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
    @Column(name = "Product_ID")
    private Integer productId;

    @Column(name = "Description", length = 800)
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Seller_ID", nullable = false)
    private User seller;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Category_ID", nullable = false)
    private Category category;

    @Column(name = "Product_Name", nullable = false, length = 300)
    private String productName;

    @Column(name = "Price", nullable = false)
    private BigDecimal price;

    @Column(name = "Quantity", nullable = false)
    private Integer quantity;

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Size(max = 5, message = "You can upload up to 5 images for a product")
    private List<Image> images = new ArrayList<>();

    public Product(String description, User seller, Category category, String productName, BigDecimal price, Integer quantity,  List<Image> images) {
        this.description = description;
        this.seller = seller;
        this.category = category;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.images = images != null ? images : new ArrayList<>();
    }

}