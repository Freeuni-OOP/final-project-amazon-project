package com.amazon.amazon_backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserRequest {
    private String username;
    private String email;
    private String gender;
    private LocalDate birthDate;
    private String password;
}