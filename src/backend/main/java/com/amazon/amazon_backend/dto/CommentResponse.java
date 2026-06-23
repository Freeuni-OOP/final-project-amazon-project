package com.amazon.amazon_backend.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {
    private Integer commentId;
    private String commentString;
    private Integer productId;
    private Integer userId;
    private String username;
}
