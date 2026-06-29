package com.amazon.amazon_backend.service;

import com.amazon.amazon_backend.dto.SignInRequest;
import com.amazon.amazon_backend.dto.UserResponse;
import com.amazon.amazon_backend.model.User;
import com.amazon.amazon_backend.repository.UserRepository;
import com.amazon.amazon_backend.utility.PassEncryption;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User mockUser;
    private String rawPassword = "password123";
    private String hashedPassword;

    @BeforeEach
    void setUp() {
        hashedPassword = PassEncryption.hashPassword(rawPassword);

        mockUser = new User();
        mockUser.setId(1);
        mockUser.setEmail("test@amazon.com");
        mockUser.setUsername("testuser");
        mockUser.setPassEncrypted(hashedPassword);
        mockUser.setBalance(BigDecimal.valueOf(100.0));
    }

    @Test
    void signInUser_Success() {

        SignInRequest request = new SignInRequest("test@amazon.com", rawPassword);
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(mockUser));

        UserResponse response = userService.signInUser(request);

        assertNotNull(response);
        assertEquals(mockUser.getEmail(), response.getEmail());
        assertEquals(mockUser.getUsername(), response.getUsername());
        verify(userRepository, times(1)).findByEmail(request.getEmail());
    }

    @Test
    void signInUser_ThrowsException_WhenUserNotFound() {
        SignInRequest request = new SignInRequest("wrong@amazon.com", rawPassword);
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.signInUser(request);
        });

        assertEquals("User not found with email: wrong@amazon.com", exception.getMessage());
        verify(userRepository, times(1)).findByEmail(request.getEmail());
    }

    @Test
    void signInUser_ThrowsException_WhenPasswordIsInvalid() {
        SignInRequest request = new SignInRequest("test@amazon.com", "wrong_password");
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(mockUser));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.signInUser(request);
        });

        assertEquals("Invalid password!", exception.getMessage());
        verify(userRepository, times(1)).findByEmail(request.getEmail());
    }
}
