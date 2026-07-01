package com.amazon.amazon_backend.service;

import com.amazon.amazon_backend.dto.CommentResponse;
import com.amazon.amazon_backend.model.Comment;
import com.amazon.amazon_backend.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.amazon.amazon_backend.utility.CommentConverter.toCommentResponseList;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public List<CommentResponse> getCommentsByProduct(Integer productId){
        List<Comment> comments = commentRepository.findByProduct_ProductId(productId);
        return toCommentResponseList(comments);
    }

}
