package com.amazon.amazon_backend.utility;

import com.amazon.amazon_backend.dto.CartItemResponse;
import com.amazon.amazon_backend.model.CartItem;
import com.amazon.amazon_backend.model.Category;
import com.amazon.amazon_backend.model.Product;
import com.amazon.amazon_backend.model.User;
import com.amazon.amazon_backend.utility.CartItemConverter;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CartItemConverterTest {

    @Test
    public void testToCartItemResponseOnTrueCases() {
        User seller = new User();
        Category category = new Category("Electronics");
        Product product = new Product("A great laptop", seller, category, "Laptop", BigDecimal.valueOf(999.99), 10, null);

        CartItem cartItem = new CartItem();
        cartItem.setId(1);
        cartItem.setUser(new User());
        cartItem.setProduct(product);
        cartItem.setQuantity(3);

        CartItemResponse response = CartItemConverter.toCartItemResponse(cartItem);

        assertNotNull(response);
        assertEquals(1, response.getId());
        assertEquals(product.getProductId(), response.getProductId());
        assertEquals("Laptop", response.getProductName());
        assertEquals(BigDecimal.valueOf(999.99), response.getPrice());
        assertEquals(3, response.getQuantity());
    }

    @Test
    public void testToCartItemResponseOnFalseCases() {
        User seller = new User();
        Category category1 = new Category("Home");
        Product product1 = new Product("Description1", seller, category1, "Chair", BigDecimal.valueOf(149.50), 5, null);

        Category category2 = new Category("Sport");
        Product product2 = new Product("Description2", seller, category2, "Football", BigDecimal.valueOf(29.99), 15, null);

        CartItem cartItem1 = new CartItem();
        cartItem1.setId(1);
        cartItem1.setUser(seller);
        cartItem1.setProduct(product1);
        cartItem1.setQuantity(2);

        CartItem cartItem2 = new CartItem();
        cartItem2.setId(2);
        cartItem2.setUser(seller);
        cartItem2.setProduct(product2);
        cartItem2.setQuantity(4);

        CartItemResponse response1 = CartItemConverter.toCartItemResponse(cartItem1);
        CartItemResponse response2 = CartItemConverter.toCartItemResponse(cartItem2);

        assertNotEquals(cartItem1.getProduct().getProductName(), response2.getProductName());
        assertNotEquals(cartItem2.getQuantity(), response1.getQuantity());
        assertNotEquals(cartItem1.getId(), response2.getId());
    }

    @Test
    public void testToCartItemResponseListOnTrueCases() {
        User seller = new User();
        Category category = new Category("Electronics");

        Product product1 = new Product("Description1", seller, category, "Laptop", BigDecimal.valueOf(999.99), 10, null);
        Product product2 = new Product("Description2", seller, category, "Phone", BigDecimal.valueOf(599.99), 20, null);

        CartItem cartItem1 = new CartItem();
        cartItem1.setId(1);
        cartItem1.setUser(seller);
        cartItem1.setProduct(product1);
        cartItem1.setQuantity(2);

        CartItem cartItem2 = new CartItem();
        cartItem2.setId(2);
        cartItem2.setUser(seller);
        cartItem2.setProduct(product2);
        cartItem2.setQuantity(1);

        List<CartItemResponse> responses = CartItemConverter.toCartItemResponseList(List.of(cartItem1, cartItem2));

        assertNotNull(responses);
        assertEquals(2, responses.size());
        assertEquals("Laptop", responses.get(0).getProductName());
        assertEquals("Phone", responses.get(1).getProductName());
    }
}