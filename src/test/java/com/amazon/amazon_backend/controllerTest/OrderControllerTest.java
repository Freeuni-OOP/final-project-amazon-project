package com.amazon.amazon_backend.controllerTest;

import com.amazon.amazon_backend.controller.OrderController;
import com.amazon.amazon_backend.dto.OrderResponse;
import com.amazon.amazon_backend.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {

    private MockMvc mockMvc;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    public void createOrder_ShouldReturnStatusOk() throws Exception {
        Integer userId = 1;
        OrderResponse mockResponse = new OrderResponse();
        mockResponse.setUserId(10);
        mockResponse.setTotalAmount(BigDecimal.valueOf(200));

        when(orderService.createOrder(userId)).thenReturn(mockResponse);

        mockMvc.perform(post("/orders/create/" + userId)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.userId").value(10))
                .andExpect(jsonPath("$.totalAmount").value(200))
                .andDo(print());
    }

    @Test
    public void getOrdersById_ShouldReturnList() throws Exception {
        Integer userId = 1;
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setUserId(userId);
        orderResponse.setTotalAmount(BigDecimal.valueOf(150));

        List<OrderResponse> mockList = Collections.singletonList(orderResponse);

        // This matches your service method: orderService.getOrders(userId)
        when(orderService.getOrders(userId)).thenReturn(mockList);

        mockMvc.perform(get("/orders/user/" + userId)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].userId").value(userId))
                .andExpect(jsonPath("$[0].totalAmount").value(150))
                .andDo(print());
    }
}