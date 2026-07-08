package com.amazon.amazon_backend.controller;

import com.amazon.amazon_backend.dto.CommentRequest;
import com.amazon.amazon_backend.dto.CommentResponse;
import com.amazon.amazon_backend.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/products/{productId}/comments/{userId}")
    public ResponseEntity<CommentResponse> createComment(
            @PathVariable Integer productId,
            @PathVariable Integer userId,
            @Valid @RequestBody CommentRequest request) {

        CommentResponse response = commentService.addComment(productId, userId, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/products/{productId}/comments")
    public ResponseEntity<List<CommentResponse>> getProductComments(@PathVariable Integer productId) {
        List<CommentResponse> comments = commentService.getCommentsByProduct(productId);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/user/{userId}/comments")
    public ResponseEntity<List<CommentResponse>> getUserComments(@PathVariable Integer userId) {
        List<CommentResponse> comments = commentService.getCommentsByUser(userId);
        return ResponseEntity.ok(comments);
    }

}
