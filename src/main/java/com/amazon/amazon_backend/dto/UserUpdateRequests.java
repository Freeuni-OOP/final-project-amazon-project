package com.amazon.amazon_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

public class UserUpdateRequests {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BalanceUpdateRequest {
        private BigDecimal expense;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UsernameUpdateRequest {
        private String username;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EmailUpdateRequest {
        private String email;
    }
}
