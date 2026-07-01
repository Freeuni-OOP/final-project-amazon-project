package com.amazon.amazon_backend.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {
    private Integer commentId;
    private String commentString;
    private LocalDateTime createdAt;
    private Integer userId;
    private String username;
}
