package com.amazon.amazon_backend.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testUserEntityState() {
        User user = new User();
        user.setId(1);
        user.setUsername("luka");
        user.setBalance(BigDecimal.valueOf(50.0));

        assertEquals(1, user.getId());
        assertEquals("luka", user.getUsername());
        assertEquals(0, BigDecimal.valueOf(50.0).compareTo(user.getBalance()));
    }

    @Test
    void testBalanceUpdates() {
        User user = new User();
        user.setBalance(BigDecimal.valueOf(100.0));

        user.setBalance(user.getBalance().add(BigDecimal.valueOf(50.0)));
        assertEquals(0, BigDecimal.valueOf(150.0).compareTo(user.getBalance()));
    }
}