package com.amazon.amazon_backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
    private Integer id;
    private String username;
    private String email;
    private Double balance;
    private String gender;
}