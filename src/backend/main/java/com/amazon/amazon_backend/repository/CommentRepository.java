package com.amazon.amazon_backend.repository;

import com.amazon.amazon_backend.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByProductId(Integer productId);
    boolean existsByProductId(Integer productId);

    List<Comment> findByUserId(Integer userId);
    boolean existsByUserId(Integer userId);
}
