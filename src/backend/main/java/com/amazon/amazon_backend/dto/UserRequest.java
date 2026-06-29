package com.amazon.amazon_backend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@Builder
public class UserRequest {
    private String username;
    private String email;
    private String gender;
    private Date birthDate;
    private String password;
}