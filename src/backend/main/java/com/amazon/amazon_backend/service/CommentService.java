package com.amazon.amazon_backend.service;

import com.amazon.amazon_backend.dto.CommentRequest;
import com.amazon.amazon_backend.dto.CommentResponse;
import com.amazon.amazon_backend.model.Comment;
import com.amazon.amazon_backend.model.Product;
import com.amazon.amazon_backend.model.User;
import com.amazon.amazon_backend.repository.CommentRepository;
import com.amazon.amazon_backend.repository.ProductRepository;
import com.amazon.amazon_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.amazon.amazon_backend.utility.CommentConverter.toCommentResponse;
import static com.amazon.amazon_backend.utility.CommentConverter.toCommentResponseList;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<CommentResponse> getCommentsByProduct(Integer productId){
        List<Comment> comments = commentRepository.findByProduct_ProductId(productId);
        return toCommentResponseList(comments);
    }

    @Transactional
    public CommentResponse addComment(Integer productId, Integer userId, CommentRequest request){
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Comment comment = new Comment(request.getCommentStr(), product, user);
        Comment savedComment = commentRepository.save(comment);

        return toCommentResponse(savedComment);
    }

}
