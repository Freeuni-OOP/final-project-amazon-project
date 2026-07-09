package com.amazon.amazon_backend.utility;

import com.amazon.amazon_backend.dto.CommentResponse;
import com.amazon.amazon_backend.model.Comment;

import java.util.List;
import java.util.stream.Collectors;

public class CommentConverter {

    public static CommentResponse toCommentResponse(Comment comment){
        return CommentResponse.builder()
                .commentId(comment.getCommentId())
                .commentString(comment.getCommentStr())
                .createdAt(comment.getCreatedAt())
                .username(comment.getUser().getUsername())
                .userId(comment.getUser().getId())
                .build();
    }

    public static List<CommentResponse> toCommentResponseList(List<Comment> comments){
        return comments.stream()
                .map(CommentConverter::toCommentResponse)
                .collect(Collectors.toList());
    }

}
