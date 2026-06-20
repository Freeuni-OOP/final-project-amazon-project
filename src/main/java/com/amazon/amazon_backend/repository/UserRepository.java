package com.amazon.amazon_backend.repository;

import com.amazon.amazon_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String UserName);
    boolean existsByUsername(String username);

    Optional<User> findByEmail(String Email);
    boolean existsByEmail(String email);
}
