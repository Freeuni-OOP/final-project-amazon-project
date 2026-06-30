package com.amazon.amazon_backend.controller;

import com.amazon.amazon_backend.controller.CartItemController;
import com.amazon.amazon_backend.dto.CartItemRequest;
import com.amazon.amazon_backend.dto.CartItemResponse;
import com.amazon.amazon_backend.service.CartItemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MediaType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class CartItemControllerTest {

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private CartItemService service;

    @InjectMocks
    private CartItemController cartItemController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(cartItemController).build();
    }

    @Test
    public void testGetCartItemById_ShouldReturnCartItem_WhenFound() throws Exception {
        Integer cartItemId = 1;
        CartItemResponse mockResponse = new CartItemResponse(cartItemId, 1, "Laptop", BigDecimal.valueOf(999.99), null, 2);

        when(service.getCartItemById(cartItemId)).thenReturn(mockResponse);

        mockMvc.perform(get("/cartItem/" + cartItemId)
                        .accept(String.valueOf(MediaType.APPLICATION_JSON)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.productName").value("Laptop"))
                .andDo(print());
    }

    @Test
    public void testGetCartItemsByUserId_ShouldReturnCartItems_WhenFound() throws Exception {
        Integer userId = 1;
        CartItemResponse mockResponse = new CartItemResponse(1, 1, "Laptop", BigDecimal.valueOf(999.99), null,2);

        when(service.searchCartItemsByUserId(userId)).thenReturn(List.of(mockResponse));

        mockMvc.perform(get("/cartItem/user/" + userId)
                        .accept(String.valueOf(MediaType.APPLICATION_JSON)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productName").value("Laptop"))
                .andDo(print());
    }

    @Test
    public void testGetCartItemsByProductId_ShouldReturnCartItems_WhenFound() throws Exception {
        Integer productId = 1;
        CartItemResponse mockResponse = new CartItemResponse(1, productId, "Laptop", BigDecimal.valueOf(999.99), null, 2);

        when(service.searchCartItemsByProductId(productId)).thenReturn(List.of(mockResponse));

        mockMvc.perform(get("/cartItem/product/" + productId)
                        .accept(String.valueOf(MediaType.APPLICATION_JSON)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productId").value(productId))
                .andDo(print());
    }

    @Test
    public void testAddCartItem_ShouldReturnAddedCartItem() throws Exception {
        CartItemRequest request = new CartItemRequest(1, 1, 2);
        CartItemResponse mockResponse = new CartItemResponse(1, 1, "Laptop", BigDecimal.valueOf(999.99), null, 2);

        when(service.addCartItem(ArgumentMatchers.any(CartItemRequest.class))).thenReturn(mockResponse);

        mockMvc.perform(post("/cartItem/add")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(objectMapper.writeValueAsString(request))
                        .accept(String.valueOf(MediaType.APPLICATION_JSON)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("Laptop"))
                .andExpect(jsonPath("$.quantity").value(2))
                .andDo(print());
    }

    @Test
    public void testUpdateCartItem_ShouldReturnUpdatedCartItem() throws Exception {
        CartItemRequest request = new CartItemRequest(1, 1, 5);
        CartItemResponse mockResponse = new CartItemResponse(1, 1, "Laptop", BigDecimal.valueOf(999.99), null, 5);

        when(service.updateCartItem(ArgumentMatchers.any(CartItemRequest.class))).thenReturn(mockResponse);

        mockMvc.perform(post("/cartItem/update")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(objectMapper.writeValueAsString(request))
                        .accept(String.valueOf(MediaType.APPLICATION_JSON)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(5))
                .andDo(print());
    }



    @Test
    public void testRemoveCartItem_ShouldReturnSuccessMessage() throws Exception {
        Integer cartItemId = 1;

        mockMvc.perform(delete("/cartItem/" + cartItemId)
                        .accept(String.valueOf(MediaType.APPLICATION_JSON)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Item successfully removed from the cart."))
                .andDo(print());
    }

}