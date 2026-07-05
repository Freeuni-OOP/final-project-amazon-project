package com.amazon.amazon_backend.model;

import com.amazon.amazon_backend.model.CartItem;
import com.amazon.amazon_backend.model.Product;
import com.amazon.amazon_backend.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


public class CartItemTest {

    @Test
    public void testAllArgsConstructorAndGetters(){
        User user1 = new User();
        Product product1 = new Product();
        CartItem cartItem1 = new CartItem(1,user1, product1,5);
        assertEquals(1,cartItem1.getId());
        assertEquals(user1, cartItem1.getUser());
        assertEquals(product1, cartItem1.getProduct());
        assertEquals(5, cartItem1.getQuantity());

        User user2 = new User();
        Product product2 = new Product();
        CartItem cartItem2 = new CartItem(2, user2, product2, 7);
        assertEquals(2, cartItem2.getId());
        assertEquals(user2, cartItem2.getUser());
        assertEquals(product2, cartItem2.getProduct());
        assertEquals(7, cartItem2.getQuantity());
    }

    @Test
    public void testNoArgsConstructor() {
        CartItem emptyCartItem = new CartItem();
        assertNull(emptyCartItem.getId());
        assertNull(emptyCartItem.getUser());
        assertNull(emptyCartItem.getProduct());
        assertNull(emptyCartItem.getQuantity());
    }

    @Test
    public void testSetters() {
        CartItem cartItem = new CartItem();
        User user = new User();
        Product product = new Product();

        cartItem.setUser(user);
        cartItem.setProduct(product);
        cartItem.setQuantity(4);

        assertEquals(user, cartItem.getUser());
        assertEquals(product, cartItem.getProduct());
        assertEquals(4, cartItem.getQuantity());
    }

}
