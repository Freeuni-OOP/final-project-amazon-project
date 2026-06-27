package com.amazon.amazon_backend.controller;

import com.amazon.amazon_backend.dto.SignInRequest;
import com.amazon.amazon_backend.dto.UserResponse;
import com.amazon.amazon_backend.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AuthControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthController authController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    void signIn_ShouldReturnUserResponse_WhenCredentialsAreValid() throws Exception {
        SignInRequest userRequest = new SignInRequest("test@amazon.com", "password123");

        UserResponse expectedResponse = UserResponse.builder()
                .id(1)
                .email("test@amazon.com")
                .username("testuser")
                .balance(BigDecimal.valueOf(100))
                .build();

        when(userService.signInUser(any(SignInRequest.class))).thenReturn(expectedResponse);

        mockMvc.perform(post("/auth/signIn")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@amazon.com"))
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.id").exists());
    }
}