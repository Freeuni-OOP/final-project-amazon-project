package com.amazon.amazon_backend.utility;

import com.amazon.amazon_backend.dto.UserResponse;
import com.amazon.amazon_backend.model.User;

public class UserConverter {
    public static UserResponse toUserResponse(User user) {
        if (user == null) {
            return null;
        }

        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setBalance(user.getBalance());
        response.setGender(user.getGender());
        response.setBirthday(user.getBirthDate());

        return response;
    }
}