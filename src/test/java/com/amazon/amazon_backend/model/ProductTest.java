package com.amazon.amazon_backend.model;

import com.amazon.amazon_backend.model.Category;
import com.amazon.amazon_backend.model.Image;
import com.amazon.amazon_backend.model.Product;
import com.amazon.amazon_backend.model.User;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ProductTest {

    @Test
    public void testCustomConstructorAndGetters() {
        User seller1 = new User();
        Category category1 = new Category("Electronics");
        List<Image> images = List.of(new Image(null, "/photos/laptop.png"));
        Product product1 = new Product("A great laptop", seller1, category1, "Laptop", BigDecimal.valueOf(999.99), 10, images);

        assertEquals("A great laptop", product1.getDescription());
        assertEquals(seller1, product1.getSeller());
        assertEquals(category1, product1.getCategory());
        assertEquals("Laptop", product1.getProductName());
        assertEquals(BigDecimal.valueOf(999.99), product1.getPrice());
        assertEquals(10, product1.getQuantity());
        assertEquals(1, product1.getImages().size());
        assertEquals("/photos/laptop.png", product1.getImages().get(0).getImageUrl());
        assertNull(product1.getProductId());
    }

    @Test
    public void testNoArgsConstructor() {
        Product emptyProduct = new Product();
        assertNull(emptyProduct.getProductId());
        assertNull(emptyProduct.getDescription());
        assertNull(emptyProduct.getSeller());
        assertNull(emptyProduct.getCategory());
        assertNull(emptyProduct.getProductName());
        assertNull(emptyProduct.getPrice());
        assertNull(emptyProduct.getQuantity());
    }

    @Test
    public void testBuilder() {
        User seller = new User();
        Category category = new Category("Sport");
        List<Image> images = List.of(new Image(null, "/photos/football.png"));

        Product product = Product.builder()
                .productName("Football")
                .description("Official size football")
                .price(BigDecimal.valueOf(29.99))
                .quantity(15)
                .seller(seller)
                .category(category)
                .images(images)
                .build();

        assertEquals("Football", product.getProductName());
        assertEquals("Official size football", product.getDescription());
        assertEquals(BigDecimal.valueOf(29.99), product.getPrice());
        assertEquals(15, product.getQuantity());
        assertEquals(seller, product.getSeller());
        assertEquals(category, product.getCategory());
        assertEquals("/photos/football.png", product.getImages().get(0).getImageUrl());
        assertNull(product.getProductId());
    }
}