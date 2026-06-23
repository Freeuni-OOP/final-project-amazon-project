package com.amazon.amazon_backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class UserResponse {
    private Integer id;
    private String username;
    private String email;
    private BigDecimal balance;
    private String gender;
    private Date Birthday;
}