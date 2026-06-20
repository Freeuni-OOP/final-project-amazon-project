package com.amazon.amazon_backend.service;

import com.amazon.amazon_backend.dto.UserRequest;
import com.amazon.amazon_backend.dto.UserResponse;
import com.amazon.amazon_backend.dto.UserUpdateRequests;
import com.amazon.amazon_backend.model.User;
import com.amazon.amazon_backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

import static com.amazon.amazon_backend.utility.UserConverter.toUserResponse;


@Service
public class UserService {
    private static final Integer minPassLen = 8;

    private static final BigDecimal INITIAL_BALANCE = BigDecimal.valueOf(1000.00);

    @Autowired
    private UserRepository userRepository;

    public UserResponse getUserById(Integer id) {
        return toUserResponse(userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("User not found.")));
    }

    public UserResponse getUserByEmail(String email) {
        return toUserResponse(userRepository.findByEmail(email).orElseThrow(() -> new NoSuchElementException("User not found with email: " + email)));
    }

    public UserResponse getUserByUsername(String username) {
        return toUserResponse(userRepository.findByUsername(username).orElseThrow(() -> new NoSuchElementException("User not found with username: " + username)));
    }

    @Transactional
    public UserResponse createUser(UserRequest request) {
        if (request.getUsername() == null || request.getUsername().isBlank()) {
            throw new IllegalArgumentException("Username is required.");
        }

        if (request.getEmail() == null || request.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email is required.");
        }

        if (request.getBirthDate() == null ) {
            throw new IllegalArgumentException("Birthday is required.");
        }

        if (request.getPassword() == null || request.getPassword().length() < minPassLen) {
            throw new IllegalArgumentException("Password must be at least 8 characters long!");
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("User with this username already exists.");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("User with this email already exists.");
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .passEncrypted(request.getPassword())
                .gender(request.getGender())
                .birthDate(request.getBirthDate())
                .balance(INITIAL_BALANCE)
                .build();

        return toUserResponse(userRepository.save(user));
    }

    private User findUser(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("User not found with ID: " + id));
    }

    @Transactional
    public UserResponse updateBalance(Integer id, UserUpdateRequests.BalanceUpdateRequest request) {
        if (request.getExpense() == null || request.getExpense().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Expense cannot be negative.");
        }

        User user = findUser(id);

        if (user.getBalance().compareTo(request.getExpense()) < 0) {
            throw new IllegalArgumentException("Insufficient balance for this transaction.");
        }

        BigDecimal newBalance = user.getBalance().subtract(request.getExpense());
        user.setBalance(newBalance);

        return toUserResponse(userRepository.save(user));
    }

    @Transactional
    public UserResponse updateUsername(Integer id, UserUpdateRequests.UsernameUpdateRequest request) {
        User user = findUser(id);

        if (request.getUsername() != null && !request.getUsername().isBlank()) {
            user.setUsername(request.getUsername());
        }
        return toUserResponse(userRepository.save(user));
    }

    @Transactional
    public UserResponse updateEmail(Integer id, UserUpdateRequests.EmailUpdateRequest request) {
        User user = findUser(id);

        if (request.getEmail() != null && !request.getEmail().isBlank()) {
            user.setEmail(request.getEmail());
        }
        return toUserResponse(userRepository.save(user));
    }

}
