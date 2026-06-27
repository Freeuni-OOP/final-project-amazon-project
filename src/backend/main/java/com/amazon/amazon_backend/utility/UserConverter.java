package com.amazon.amazon_backend.utility;

import com.amazon.amazon_backend.dto.UserResponse;
import com.amazon.amazon_backend.model.User;

public class UserConverter {
    public static UserResponse toUserResponse(User user) {
        if (user == null) {
            return null;
        }

        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .balance(user.getBalance())
                .gender(user.getGender())
                .birthDate(user.getBirthDate())
                .build();
    }
}