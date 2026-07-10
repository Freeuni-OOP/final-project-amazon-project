package com.amazon.amazon_backend.utility;

import com.amazon.amazon_backend.dto.ProductResponse;
import com.amazon.amazon_backend.model.Category;
import com.amazon.amazon_backend.model.Image;
import com.amazon.amazon_backend.model.Product;
import com.amazon.amazon_backend.model.User;
import com.amazon.amazon_backend.utility.ProductConverter;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProductConverterTest {

    @Test
    public void testToProductResponseOnTrueCases() {
        User seller = new User();
        seller.setUsername("sellerUser");

        Category category = new Category("Electronics");

        Product product = new Product("A great laptop", seller, category, "Laptop", BigDecimal.valueOf(999.99), 10, new ArrayList<>());
        Image image = new Image(null, "/photos/laptop.png");
        image.setProduct(product);
        product.getImages().add(image);

        ProductResponse response = ProductConverter.toProductResponse(product, List.of(), List.of(),false);

        assertNotNull(response);
        assertEquals(product.getProductId(), response.getProductId());
        assertEquals("Laptop", response.getProductName());
        assertEquals(BigDecimal.valueOf(999.99), response.getPrice());
        assertEquals(10, response.getQuantity());
        assertEquals("Electronics", response.getCategoryName());
        assertEquals("sellerUser", response.getSellerName());
        assertEquals(1, response.getImageUrls().size());
        assertEquals("/photos/laptop.png", response.getImageUrls().get(0));
    }

    @Test
    public void testToProductResponseOnFalseCases() {
        User seller1 = new User();
        seller1.setUsername("sellerOne");
        Category category1 = new Category("Home");
        Product product1 = new Product("Description1", seller1, category1, "Chair", BigDecimal.valueOf(149.50), 5, new ArrayList<>());

        User seller2 = new User();
        seller2.setUsername("sellerTwo");
        Category category2 = new Category("Sport");
        Product product2 = new Product("Description2", seller2, category2, "Football", BigDecimal.valueOf(29.99), 15, new ArrayList<>());

        ProductResponse response1 = ProductConverter.toProductResponse(product1, List.of(), List.of(),false);
        ProductResponse response2 = ProductConverter.toProductResponse(product2, List.of(), List.of(),false);

        assertNotEquals(product1.getProductName(), response2.getProductName());
        assertNotEquals(product2.getQuantity(), response1.getQuantity());
        assertNotEquals(response1.getSellerName(), response2.getSellerName());
        assertNotEquals(response1.getCategoryName(), response2.getCategoryName());
    }

    @Test
    public void testToProductResponseReturnsNullWhenProductIsNull() {
        assertNull(ProductConverter.toProductResponse(null, List.of(), List.of(),false));
    }

    @Test
    public void testToProductResponseListOnTrueCases() {
        User seller = new User();
        seller.setUsername("sellerUser");
        Category category = new Category("Electronics");

        Product product1 = new Product("Description1", seller, category, "Laptop", BigDecimal.valueOf(999.99), 10, new ArrayList<>());
        Product product2 = new Product("Description2", seller, category, "Phone", BigDecimal.valueOf(599.99), 20, new ArrayList<>());

        List<ProductResponse> responses = ProductConverter.toProductResponseList(List.of(product1, product2));

        assertNotNull(responses);
        assertEquals(2, responses.size());
        assertEquals("Laptop", responses.get(0).getProductName());
        assertEquals("Phone", responses.get(1).getProductName());
    }

    @Test
    public void testToProductResponseListReturnsEmptyListWhenNull() {
        List<ProductResponse> responses = ProductConverter.toProductResponseList(null);

        assertNotNull(responses);
        assertTrue(responses.isEmpty());
    }
}