package com.amazon.amazon_backend.repository;

import com.amazon.amazon_backend.model.Comment;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByProduct_ProductId(Integer productId);
    List<Comment> findByProduct_ProductIdOrderByCreatedAtDesc(Integer productProductId);
    List<Comment> findByUser_IdOrderByCreatedAtDesc(Integer userId);
    List<Comment> findByUserId(Integer userId);
    List<Comment> findTop5ByProduct_ProductIdOrderByCommentIdDesc(Integer productId);
    boolean existsByUserId(Integer userId);
    boolean existsByProduct_productId(Integer productId);
}
