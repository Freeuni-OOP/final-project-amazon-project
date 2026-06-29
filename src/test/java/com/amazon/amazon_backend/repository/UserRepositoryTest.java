package com.amazon.amazon_backend.repository;

import com.amazon.amazon_backend.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testUserRepositoryOnIds() {
        User user1 = User.builder()
                .username("user1")
                .email("user1@amazon.com")
                .passEncrypted("pass123")
                .balance(BigDecimal.valueOf(100.0))
                .gender("Male")
                .birthDate(new java.util.Date())
                .build();

        User savedUser1 = userRepository.save(user1);
        assertNotNull(savedUser1.getId());

        User user2 = User.builder()
                .username("user2")
                .email("user2@amazon.com")
                .passEncrypted("pass456")
                .balance(BigDecimal.valueOf(100.0))
                .gender("Female")
                .birthDate(new java.util.Date())
                .build();

        User savedUser2 = userRepository.save(user2);
        assertNotNull(savedUser2.getId());
    }

    @Test
    public void testUserRepositoryOnEmailsAndUsernames() {
        User user = User.builder()
                .username("data_test")
                .email("data@amazon.com")
                .passEncrypted("hashed_password")
                .balance(BigDecimal.valueOf(100.0))
                .birthDate(new java.util.Date())
                .build();

        User savedUser = userRepository.save(user);

        assertEquals("data_test", savedUser.getUsername());
        assertEquals("data@amazon.com", savedUser.getEmail());
    }

    @Test
    public void testUserRepositoryFindByEmail() {
        User user = User.builder()
                .username("data_test")
                .email("data@amazon.com")
                .passEncrypted("hashed_password")
                .balance(BigDecimal.valueOf(100.0))
                .birthDate(new java.util.Date())
                .build();

        userRepository.save(user);

        // ტესტი როცა იუზერი არსებობს
        Optional<User> foundUser = userRepository.findByEmail("data@amazon.com");
        assertTrue(foundUser.isPresent());
        assertEquals("data_test", foundUser.get().getUsername());

        // ტესტი როცა იუზერი არ არსებობს
        Optional<User> nonexistentUser = userRepository.findByEmail("nonexistent@amazon.com");
        assertTrue(nonexistentUser.isEmpty());
    }

    @Test
    public void testUserRepositoryOnCount() {
        assertEquals(0, userRepository.count());

        User user1 = User.builder()
                .username("user1")
                .email("user1@amazon.com")
                .passEncrypted("pass123")
                .balance(BigDecimal.valueOf(100.0))
                .birthDate(new java.util.Date())
                .build();

        userRepository.save(user1);
        assertEquals(1, userRepository.count());

        User user2 = User.builder()
                .username("user2")
                .email("user2@amazon.com")
                .passEncrypted("pass456")
                .balance(BigDecimal.valueOf(50.0))
                .birthDate(new java.util.Date())
                .build();

        userRepository.save(user2);
        assertEquals(2, userRepository.count());
    }

    @Test
    public void testUserRepositoryOnDelete() {
        User user1 = User.builder()
                .username("user1")
                .email("user1@amazon.com")
                .passEncrypted("pass123")
                .balance(BigDecimal.valueOf(100.0))
                .birthDate(new java.util.Date())
                .build();
        User savedUser1 = userRepository.save(user1);

        User user2 = User.builder()
                .username("user2")
                .email("user2@amazon.com")
                .passEncrypted("pass456")
                .balance(BigDecimal.valueOf(50.0))
                .birthDate(new java.util.Date())
                .build();
        User savedUser2 = userRepository.save(user2);

        assertEquals(2, userRepository.count());

        userRepository.deleteById(savedUser1.getId());
        assertEquals(1, userRepository.count());

        userRepository.deleteById(savedUser2.getId());
        assertEquals(0, userRepository.count());
    }
}