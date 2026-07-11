package com.amazon.amazon_backend.service;

import com.amazon.amazon_backend.dto.CommentRequest;
import com.amazon.amazon_backend.dto.CommentResponse;
import com.amazon.amazon_backend.model.Comment;
import com.amazon.amazon_backend.model.Product;
import com.amazon.amazon_backend.model.User;
import com.amazon.amazon_backend.repository.CommentRepository;
import com.amazon.amazon_backend.repository.ProductRepository;
import com.amazon.amazon_backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

import static com.amazon.amazon_backend.utility.CommentConverter.toCommentResponse;
import static com.amazon.amazon_backend.utility.CommentConverter.toCommentResponseList;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository, ProductRepository productRepository, UserRepository userRepository){
        this.commentRepository = commentRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> getCommentsByProduct(Integer productId){
        List<Comment> comments = commentRepository.findByProduct_ProductIdOrderByCreatedAtDesc(productId);
        return toCommentResponseList(comments);
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> getCommentsByUser(Integer userId){
        List<Comment> comments = commentRepository.findByUser_IdOrderByCreatedAtDesc(userId);
        return toCommentResponseList(comments);
    }

    @Transactional
    public CommentResponse addComment(Integer productId, Integer userId, CommentRequest request){
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("Product not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        Comment comment = new Comment(request.getCommentStr(), product, user);
        Comment savedComment = commentRepository.save(comment);

        return toCommentResponse(savedComment);
    }

}
