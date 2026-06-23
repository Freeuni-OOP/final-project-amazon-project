package com.amazon.amazon_backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInRequest {
    private String email;
    private String password;
}
