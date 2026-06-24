package com.amazon.amazon_backend.controller;

import com.amazon.amazon_backend.dto.SignInRequest;
import com.amazon.amazon_backend.dto.UserRequest;
import com.amazon.amazon_backend.dto.UserResponse;
import com.amazon.amazon_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/signUp")
    public ResponseEntity<UserResponse> signUp(UserRequest request) {
        return ResponseEntity.ok(userService.createUser(request));
    }

    @PostMapping("/signIn")
    public ResponseEntity<UserResponse> logIn(SignInRequest request) {
        return ResponseEntity.ok(userService.signInUser(request));
    }
}
