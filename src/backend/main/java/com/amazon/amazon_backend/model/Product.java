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
    @Column(name = "Product_ID")
    private Integer productId;

    @Column(name = "Description", length = 800)
    private String description;

    @ManyToOne
    @JoinColumn(name = "Seller_ID", nullable = false)
    private User seller;
    
    @ManyToOne
    @JoinColumn(name = "Category_ID", nullable = false)
    private Category category;

    @Column(name = "ProductName", nullable = false, length = 300)
    private String productName;

    @Column(name = "Price", nullable = false)
    private BigDecimal price;

    @Column(name = "Quantity", nullable = false)
    private Integer quantity;

    @Column (name = "Img_Url", length = 500)
    private String imgUrl;

}
