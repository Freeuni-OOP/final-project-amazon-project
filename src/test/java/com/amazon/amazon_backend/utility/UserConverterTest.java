package com.amazon.amazon_backend.utility;

import com.amazon.amazon_backend.dto.UserResponse;
import com.amazon.amazon_backend.model.User;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class UserConverterTest {

    @Test
    void toUserResponse_ShouldMapAllFieldsCorrectly() {
        User user = new User();
        user.setId(99);
        user.setUsername("john_doe");
        user.setEmail("john@amazon.com");
        user.setBalance(BigDecimal.valueOf(250.50));
        user.setPassEncrypted("hashed_string");

        UserResponse response = UserConverter.toUserResponse(user);

        assertNotNull(response);
        assertEquals(99, response.getId());
        assertEquals("john_doe", response.getUsername());
        assertEquals("john@amazon.com", response.getEmail());
        assertEquals(0, BigDecimal.valueOf(250.50).compareTo(user.getBalance()));
    }
}