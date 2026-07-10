package com.amazon.amazon_backend.controller;

import com.amazon.amazon_backend.dto.SignInRequest;
import com.amazon.amazon_backend.dto.UserRequest;
import com.amazon.amazon_backend.dto.UserResponse;
import com.amazon.amazon_backend.dto.UserUpdateRequests;
import com.amazon.amazon_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable Integer id) {
        return userService.getUserById(id);
    }

    @GetMapping("/email/{email}")
    public UserResponse getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    @GetMapping("/username/{username}")
    public UserResponse getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }

    @PostMapping
    public UserResponse createUser(@RequestBody UserRequest request) {
        return userService.createUser(request);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(java.util.Map.of("message", ex.getMessage()));
    }
    @PostMapping("/sign-in")
    public UserResponse signInUser(@RequestBody SignInRequest request){return userService.signInUser(request);}

    @PatchMapping("/{id}/balance")
    public UserResponse updateBalance(@PathVariable Integer id, @RequestBody UserUpdateRequests.BalanceUpdateRequest request) {
        return userService.updateBalance(id, request);
    }

    @PatchMapping("/{id}/username")
    public UserResponse updateUsername(@PathVariable Integer id, @RequestBody UserUpdateRequests.UsernameUpdateRequest request) {
        return userService.updateUsername(id, request);
    }

    @PatchMapping("/{id}/email")
    public UserResponse updateEmail(@PathVariable Integer id, @RequestBody UserUpdateRequests.EmailUpdateRequest request) {
        return userService.updateEmail(id, request);
    }
}