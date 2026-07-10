package com.amazon.amazon_backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    private String username;
    private String email;
    private String gender;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date birthDate;
    private String password;
}