package com.amazon.amazon_backend.controller;

import com.amazon.amazon_backend.dto.TransactionResponse;
import com.amazon.amazon_backend.model.TransactionStatus;
import com.amazon.amazon_backend.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TransactionService transactionService;

    private TransactionResponse tranResp;


    @BeforeEach
    void setUp() {
        tranResp = new TransactionResponse();
        tranResp.setId(500);
        tranResp.setBuyerId(1);
        tranResp.setSellerId(2);
        tranResp.setTotalAmount(BigDecimal.valueOf(99.99));
        tranResp.setStatus(TransactionStatus.PENDING);
    }


    @Test
    void testGetUserHistory_ShouldReturnList() throws Exception {
        when(transactionService.getUserTransactions(1))
                .thenReturn(List.of(tranResp));

        mockMvc.perform(get("/users/1/transactions/history")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(500));
    }

    @Test
    void testGetPurchases_ShouldReturnList() throws Exception {
        when(transactionService.getUserPurchaseTransactions(1))
                .thenReturn(List.of(tranResp));

        mockMvc.perform(get("/users/1/transactions/purchases")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(500));
    }

    @Test
    void testGetSales_ShouldReturnList() throws Exception {
        when(transactionService.getUserSaleTransactions(1))
                .thenReturn(List.of(tranResp));

        mockMvc.perform(get("/users/1/transactions/sales")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(500));
    }

    @Test
    void testGetTransactionById_ShouldReturnTransaction_WhenAuthorized() throws Exception {
        when(transactionService.getTransactionById(1, 500))
                .thenReturn(tranResp);

        mockMvc.perform(get("/users/1/transactions/500")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(500))
                .andExpect(jsonPath("$.status").value("PENDING"))
                .andExpect(jsonPath("$.totalAmount").value(99.99));
    }

    @Test
    void testUpdateStatus_ShouldReturnUpdatedTransaction_WhenValid() throws Exception {
        TransactionResponse updatedTR = new TransactionResponse();
        updatedTR.setId(500);
        updatedTR.setStatus(TransactionStatus.SUCCESS);

        when(transactionService.updateTransactionStatus(1, 500, TransactionStatus.SUCCESS))
                .thenReturn(updatedTR);

        mockMvc.perform(put("/users/1/transactions/500/status")
                        .param("newStatus", "SUCCESS")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(500))
                .andExpect(jsonPath("$.status").value("SUCCESS"));
    }
}

